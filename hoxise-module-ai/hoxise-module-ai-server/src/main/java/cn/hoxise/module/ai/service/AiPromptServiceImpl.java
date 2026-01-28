package cn.hoxise.module.ai.service;

import cn.hoxise.common.base.exception.ServiceException;
import cn.hoxise.module.ai.pojo.constants.AiPromptConstants;
import cn.hoxise.module.ai.pojo.constants.RedisConstants;
import cn.hoxise.module.movie.api.bangumi.BangumiDbApi;
import cn.hoxise.module.movie.api.bangumi.BangumiDbCharacterApi;
import cn.hoxise.module.movie.api.bangumi.dto.BangumiDbCharacterDTO;
import cn.hoxise.module.movie.api.bangumi.dto.BangumiDbDTO;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提示词实现
 *
 * @author hoxise
 * @since 2026/1/28 下午1:20
 */
@Service
public class AiPromptServiceImpl implements AiPromptService{

    @Resource
    private BangumiDbApi bangumiDbApi;

    @Resource
    private BangumiDbCharacterApi characterApi;

    @Override
    @Cacheable(cacheNames = RedisConstants.AI_RECOMMEND_PROMPT_KEY)
    public String buildMovieRecommendPrompt(){
        //获取所有db数据
        List<BangumiDbDTO> list = bangumiDbApi.page(1, 9999).getCheckedData().getList();
        List<JSONObject> data = list.stream().map(m -> {
            JSONObject obj = new JSONObject();
            obj.put("id", m.getCatalogid());
            obj.put("name", m.getMatchingName());
            obj.put("originalName", m.getOriginalName());
            return obj;
        }).toList();

        return AiPromptConstants.AI_MOVIE_RECOMMEN_SYSTEM_PROMPT + JSONObject.toJSONString(data);
    }

    @Override
    @Cacheable(cacheNames = RedisConstants.AI_SUMMERY_PROMPT_KEY
            , key = "#catalogid")
    public String buildMovieSummaryPrompt(Long catalogid){
        Assert.notNull(catalogid, "目录id不能为空");

        List<BangumiDbDTO> checkedData = bangumiDbApi.list(Collections.singletonList(catalogid)).getCheckedData();
        if (checkedData.isEmpty()){
            throw new ServiceException("未找到数据");
        }
        BangumiDbDTO bangumiDb = checkedData.getFirst();

        //基础数据
        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("name", bangumiDb.getMatchingName());
        paramMap.put("originalName", bangumiDb.getOriginalName());
        paramMap.put("metaTags", String.join(",",bangumiDb.getMetaTags()));
        paramMap.put("tags", String.join(",",bangumiDb.getTags()));
        paramMap.put("airDate", bangumiDb.getReleaseDate()==null?"" : bangumiDb.getReleaseDate().toString());
        paramMap.put("rating", bangumiDb.getRating().toString());
        paramMap.put("description", bangumiDb.getSummary());

        //角色数据
        List<BangumiDbCharacterDTO> characterList = characterApi.list(Collections.singletonList(catalogid)).getCheckedData();
        if (characterList != null && !characterList.isEmpty()){
            List<String> characters = characterList.stream().map(m -> m.getName() + "(" + m.getRelation() + ")").toList();
            paramMap.put("characters", String.join(",",characters));
        }

        //提示词模板
        PromptTemplate promptTemplate = PromptTemplate.builder()
                .renderer(StTemplateRenderer.builder().startDelimiterToken('<').endDelimiterToken('>').build())
                .template(AiPromptConstants.AI_SUMMARY_PROMPT)
                .build();

        return promptTemplate.render(paramMap);
    }

}
