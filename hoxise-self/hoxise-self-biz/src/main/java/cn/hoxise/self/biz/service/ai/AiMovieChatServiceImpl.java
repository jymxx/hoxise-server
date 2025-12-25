package cn.hoxise.self.biz.service.ai;

import cn.hoxise.common.ai.api.OpenAiApi;
import cn.hoxise.common.base.utils.redis.RedisUtil;
import cn.hoxise.self.biz.dal.entity.MovieDbBangumiDO;
import cn.hoxise.self.biz.pojo.constants.AiPromptConstants;
import cn.hoxise.self.biz.pojo.constants.MovieRedisConstants;
import cn.hoxise.self.biz.service.movie.MovieDbBangumiCharacterService;
import cn.hoxise.self.biz.service.movie.MovieDbBangumiInfoboxService;
import cn.hoxise.self.biz.service.movie.MovieDbBangumiService;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

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

    @Resource
    private MovieDbBangumiInfoboxService infoboxService;

    @Resource
    private AiRequestRecordService aiRequestRecordService;

    @Override
    public Flux<String> aiSummary(Long catalogid){
        return openAiApi.getChatClient().prompt()
                .user(getAiSummaryPromptCache(catalogid))
                .stream()
                .content();
    }

    /**
     * 从缓存获得构建的提示词
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
        paramMap.put("airDate", bangumiDO.getReleaseDate().toString());
        paramMap.put("rating", bangumiDO.getRating().toString());
        paramMap.put("characters", String.join(",",characters));
        paramMap.put("description", bangumiDO.getSummary());

        String format = StrUtil.format(AiPromptConstants.AI_SUMMARY_PROMPT, paramMap);

        if (StrUtil.isNotBlank(format)){
            RedisUtil.setValue(redisKey,format);
        }
        return format;
    }
}
