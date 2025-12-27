package cn.hoxise.self.biz.service.ai;

import cn.hoxise.common.ai.api.OpenAiApi;
import cn.hoxise.common.base.utils.date.DateUtil;
import cn.hoxise.common.base.utils.redis.RedisUtil;
import cn.hoxise.self.biz.dal.entity.MovieDbBangumiDO;
import cn.hoxise.self.biz.pojo.constants.AiPromptConstants;
import cn.hoxise.self.biz.pojo.constants.MovieRedisConstants;
import cn.hoxise.self.biz.pojo.constants.OpenAiConstants;
import cn.hoxise.self.biz.service.movie.MovieDbBangumiCharacterService;
import cn.hoxise.self.biz.service.movie.MovieDbBangumiInfoboxService;
import cn.hoxise.self.biz.service.movie.MovieDbBangumiService;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author hoxise
 * @Description: 影视聊天实现类
 * @Date 2025-12-25 上午4:30
 */
@Service
public class AiMovieChatServiceImpl implements AiMovieChatService {

    @Resource
    private OpenAiApi openAiApi;

    @Resource
    private MovieDbBangumiService movieDbBangumiService;

    @Resource
    private MovieDbBangumiCharacterService characterService;

    @Autowired(required = false)
    private VectorStore vectorStore;

    @Override
    public Flux<String> aiRecommend(String userText, String chatId, Long userid,String mode){
        //对话id 过日强制重置
        String finalChatId = userid+"_"+ LocalDateTime.now().format(DateUtil.DATE_FORMATTER) + "_"+chatId;

        if ("reasoner".equalsIgnoreCase(mode)){
            //分析模型
            ChatClient chatClient = openAiApi.getDeepSeekReasonerClient();
            return chatClient.prompt()
                    .system(getAiRecommendPromptCache())
                    .user(userText)
                    //记忆存储
                    .advisors(a->a.param(ChatMemory.CONVERSATION_ID, finalChatId))
                    .stream()
                    .content();

        }else{
            //聊天记忆模型
            ChatClient chatClient = openAiApi.getMemoryChatClient();
            return chatClient.prompt()
                    .system(AiPromptConstants.AI_MOVIE_RECOMMEN_SYSTEM_PROMPT_RAG)
                    .user(userText)
                    //记忆存储
                    .advisors(a->a.param(ChatMemory.CONVERSATION_ID, finalChatId))
                    //RAG检索增强
                    .advisors(QuestionAnswerAdvisor.builder(vectorStore)
                            .searchRequest(SearchRequest.builder()
                                    .similarityThreshold(0.2)//最小相似度
                                    .topK(10)//按匹配度排序前N条
                                    .build()).build())
                    .stream()
                    .content();
        }

    }

    @Override
    public Flux<String> aiSummary(Long catalogid){
        return openAiApi.getChatClient().prompt()
                .user(getAiSummaryPromptCache(catalogid))
                .stream()
                .content();
    }


    /**
     * 从缓存获得构建的ai推荐提示词
     * 主要是全数据id和名称
     */
    private String getAiRecommendPromptCache(){
        String redisKey = MovieRedisConstants.AI_RECOMMEND_PROMPT_KEY;
        String value = RedisUtil.getValue(redisKey);
        if (value != null){
            return  value;
        }

        List<MovieDbBangumiDO> list = movieDbBangumiService.list();

        List<JSONObject> data = list.stream().map(m -> {
            JSONObject obj = new JSONObject();
            obj.put("catalogid", m.getCatalogid());
            obj.put("name", m.getMatchingName());
            obj.put("originalName", m.getOriginalName());
            return obj;
        }).toList();

        //将数据集拼接上去
        String resStr = AiPromptConstants.AI_MOVIE_RECOMMEN_SYSTEM_PROMPT+JSONObject.toJSONString(data);
        RedisUtil.setValue(redisKey, resStr);
        return resStr;
    }

    /**
     * 从缓存获得构建的ai总结提示词
     */
    private String getAiSummaryPromptCache(Long catalogid){
        String redisKey = MovieRedisConstants.AI_SUMMERY_PROMPT_KEY +":"+ catalogid;
        String value = RedisUtil.getValue(redisKey);
        if (value != null){
            return  value;
        }
        //拿到数据
        MovieDbBangumiDO bangumiDO = movieDbBangumiService.getByCatalogId(catalogid);
        List<String> characters = characterService.getCharacters(catalogid).stream().map(m -> m.getName() +"("+ m.getRelation()+")").toList();

        Map<String, String> paramMap = new HashMap<>(16);
        paramMap.put("name", bangumiDO.getMatchingName());
        paramMap.put("originalName", bangumiDO.getOriginalName());
        paramMap.put("metaTags", String.join(",",bangumiDO.getMetaTags()));
        paramMap.put("tags", String.join(",",bangumiDO.getTags()));
        paramMap.put("airDate", bangumiDO.getReleaseDate()==null?"" : bangumiDO.getReleaseDate().toString());
        paramMap.put("rating", bangumiDO.getRating().toString());
        paramMap.put("characters", String.join(",",characters));
        paramMap.put("description", bangumiDO.getSummary());

        String format = StrUtil.format(AiPromptConstants.AI_SUMMARY_PROMPT, paramMap);
        RedisUtil.setValue(redisKey,format);
        return format;
    }
}
