package cn.hoxise.self.ai.service;

import cn.hoxise.common.ai.core.DeepSeekApiImpl;
import cn.hoxise.common.ai.core.OpenAiApi;
import cn.hoxise.common.base.utils.date.DateUtil;
import cn.hoxise.common.framework.utils.RedisUtil;
import cn.hoxise.self.ai.pojo.constants.AiRedisKeyConstants;
import cn.hoxise.self.movie.dal.entity.MovieDbBangumiDO;
import cn.hoxise.self.ai.pojo.constants.AiPromptConstants;
import cn.hoxise.self.movie.service.movie.bangumi.MovieDbBangumiCharacterService;
import cn.hoxise.self.movie.service.movie.bangumi.MovieDbBangumiService;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.deepseek.DeepSeekAssistantMessage;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.preretrieval.query.transformation.QueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 影视AI聊天实现类
 *
 * @author hoxise
 * @since 2026/01/14 14:52:03
 */
@Service
public class AiMovieChatServiceImpl implements AiMovieChatService {

    @Resource(name = "deepSeekApiImpl")
    private OpenAiApi openAiApi;

    @Resource
    private MovieDbBangumiService movieDbBangumiService;

    @Resource
    private MovieDbBangumiCharacterService characterService;

    @Resource
    private VectorStore vectorStore;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public Flux<String> aiRecommend(String userText, String chatId, Long userid,String mode){
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
    public Flux<String> deepSeekReasoner(String userText, String chatId){
        ChatClient chatClient = openAiApi.getMemoryChatClient();
        Flux<ChatResponse> chatResponseFlux = chatClient.prompt()
                .system(AiPromptConstants.AI_MOVIE_RECOMMEN_SYSTEM_PROMPT+getAiRecommendDataCache())
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

        ChatClient chatClient = openAiApi.getMemoryChatClient();
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
    public Flux<String> aiSummary(Long catalogid){
        return openAiApi.getChatClient(DeepSeekApiImpl.MODEL_CHAT)
                .prompt(getAiSummaryPromptCache(catalogid))
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
     * 从缓存获得构建的ai推荐数据集
     * 主要是全数据id和名称
     */
    private String getAiRecommendDataCache(){
        String redisKey = AiRedisKeyConstants.AI_RECOMMEND_PROMPT_KEY;
        if (redisUtil.hasKey(redisKey)){
            return redisUtil.getValue(redisKey);
        }

        List<MovieDbBangumiDO> list = movieDbBangumiService.list();
        List<JSONObject> data = list.stream().map(m -> {
            JSONObject obj = new JSONObject();
            obj.put("id", m.getCatalogid());
            obj.put("name", m.getMatchingName());
            obj.put("originalName", m.getOriginalName());
            return obj;
        }).toList();

        String resStr = JSONObject.toJSONString(data);
        redisUtil.setValue(redisKey, resStr);

        return resStr;
    }

    /**
     * 从缓存获得构建的ai总结提示词
     */
    private String getAiSummaryPromptCache(Long catalogid){
        String redisKey = AiRedisKeyConstants.AI_SUMMERY_PROMPT_KEY +":"+ catalogid;
        if (redisUtil.hasKey(redisKey)){
            return redisUtil.getValue(redisKey);
        }
        //拿到数据
        MovieDbBangumiDO bangumiDO = movieDbBangumiService.getByCatalogId(catalogid);
        List<String> characters = characterService.getCharacters(catalogid).stream().map(m -> m.getName() +"("+ m.getRelation()+")").toList();


        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("name", bangumiDO.getMatchingName());
        paramMap.put("originalName", bangumiDO.getOriginalName());
        paramMap.put("metaTags", String.join(",",bangumiDO.getMetaTags()));
        paramMap.put("tags", String.join(",",bangumiDO.getTags()));
        paramMap.put("airDate", bangumiDO.getReleaseDate()==null?"" : bangumiDO.getReleaseDate().toString());
        paramMap.put("rating", bangumiDO.getRating().toString());
        paramMap.put("characters", String.join(",",characters));
        paramMap.put("description", bangumiDO.getSummary());

        //提示词模板
        PromptTemplate promptTemplate = PromptTemplate.builder()
                .renderer(StTemplateRenderer.builder().startDelimiterToken('<').endDelimiterToken('>').build())
                .template(AiPromptConstants.AI_SUMMARY_PROMPT)
                .build();

        String prompt = promptTemplate.render(paramMap);

        redisUtil.setValue(redisKey,prompt);
        return prompt;
    }
}
