package cn.hoxise.module.ai.service.movie;

import cn.hoxise.common.base.exception.ServiceException;
import cn.hoxise.module.ai.framework.core.deepseek.DeepSeekClient;
import cn.hoxise.module.ai.framework.core.OpenAiClient;
import cn.hoxise.common.base.utils.date.DateUtil;
import cn.hoxise.module.ai.pojo.constants.AiPromptConstants;
import cn.hoxise.module.ai.pojo.constants.RedisConstants;
import jakarta.annotation.Resource;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.deepseek.DeepSeekAssistantMessage;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 影视AI聊天实现类
 *
 * @author hoxise
 * @since 2026/01/14 14:52:03
 */
@Service
public class AiMovieChatServiceImpl implements AiMovieChatService {

    @Resource(name = "deepSeekClient")
    private OpenAiClient openAiClient;

    @Resource
    private VectorStore vectorStore;

    @Resource
    private AiPromptService aiPromptService;

    @Resource
    private RedissonClient redissonClient;

    @Override
    public Flux<String> aiRecommend(String userText, String chatId, Long userid,String mode){
        //频率限制
        aiRateLimit(userid);

        //对话id 过日强制重置
        String finalChatId = userid+"_"+ LocalDateTime.now().format(DateUtil.DATE_FORMATTER) + "_"+chatId;

        if ("reasoner".equalsIgnoreCase(mode)){
            //分析模型
            return deepSeekReasoner(userText, finalChatId);
        }else{
            //RAG模式
            return deepSeekChatRag(userText, finalChatId);
        }
    }

    /**
     * deepSeekReasoner
     *
     * @param userText 用户文本
     * @param chatId 聊天id
     * @return 结果
     * @author hoxise
     * @since 2026/01/14 21:41:49
     */
    private Flux<String> deepSeekReasoner(String userText, String chatId){
        ChatClient chatClient = openAiClient.getMemoryChatClient();
        Flux<ChatResponse> chatResponseFlux = chatClient.prompt()
                .system(aiPromptService.buildMovieRecommendPrompt())
                .user(userText)
                //记忆存储
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, chatId))
                .stream()
                .chatResponse();
        return handleReasonerResponse(chatResponseFlux);
    }

    /**
     * deepSeekChat
     *
     * @param userText 用户文本
     * @param chatId 聊天id
     * @return 结果
     * @author hoxise
     * @since 2026/01/14 21:41:49
     */
    private Flux<String> deepSeekChatRag(String userText, String chatId){

        ChatClient chatClient = openAiClient.getMemoryChatClient();
        Flux<ChatResponse> chatResponseFlux = chatClient.prompt()
                .system(AiPromptConstants.AI_MOVIE_RECOMMEN_SYSTEM_PROMPT_RAG)
                .user(userText)
                //记忆存储
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, chatId))
                //RAG检索增强
                .advisors(QuestionAnswerAdvisor.builder(vectorStore)
                        .searchRequest(SearchRequest.builder()
                                .similarityThreshold(0.2)//最小相似度
                                .topK(10)//按匹配度排序前N条
                                .build()).build())
                .stream()
                .chatResponse();
        return handleReasonerResponse(chatResponseFlux);
    }

    @Override
    public Flux<String> aiSummary(Long catalogId){
        return openAiClient.getChatClient(DeepSeekClient.MODEL_CHAT)
                .prompt(aiPromptService.buildMovieSummaryPrompt(catalogId))
                .stream()
                .content();
    }

    /**
     * 将响应结果转换为流式输出,包含思维过程
     *
     * @param chatResponseFlux chatClient的流式响应
     * @return 处理后的流
     * @author hoxise
     * @since 2026/01/14 22:57:28
     */
    private Flux<String> handleReasonerResponse(Flux<ChatResponse> chatResponseFlux){
        //是否输出思维链
        AtomicBoolean isReasoner = new AtomicBoolean( true);
        Flux<String> resFlux = Flux.just("[REASONING_START]");
        // 创建一个组合的Flux，先发送推理内容，然后发送最终结果
        return resFlux.concatWith(chatResponseFlux.flatMap(response -> {
            Generation generation = response.getResult();
            DeepSeekAssistantMessage assistantMessage = (DeepSeekAssistantMessage) generation.getOutput();

            // 获取思维链和回复
            String reasoningContent = assistantMessage.getReasoningContent();
            String finalResult = assistantMessage.getText();

            // 如果存在思维链，先发送思维链
            if (reasoningContent != null && !reasoningContent.isEmpty()) {
                return Flux.just(reasoningContent);
            }

            Flux<String> resultFlux = Flux.empty();
            // 然后发送实际回复
            if (finalResult != null && !finalResult.isEmpty()) {
                if (isReasoner.getAndSet(false)){
                    resultFlux = resultFlux.concatWith(Flux.just("[REASONING_END]"));
                }
                resultFlux = resultFlux.concatWith(Flux.just(finalResult));
            }
            return resultFlux;
        }));
    }

    /**
     * aiRateLimit 频率限制
     *
     * @param userid 用户id
     * @author hoxise
     * @since 2026/03/01 01:33:21
     */
    private void aiRateLimit(Long userid) {
        if(userid == 0L){
            return;
        }
        //限制请求频率
        String rateLimitKey = RedisConstants.AI_REQUEST_LIMIT_KEY + "::" + userid ;
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(rateLimitKey);
        //每小时限制200次
        rateLimiter.trySetRate(RateType.OVERALL,200, Duration.ofHours(1));

        if (!rateLimiter.tryAcquire()){
            throw new ServiceException("请勿频繁调用");
        }
    }

}
