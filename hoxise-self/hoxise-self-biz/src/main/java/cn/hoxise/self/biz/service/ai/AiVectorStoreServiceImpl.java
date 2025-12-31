package cn.hoxise.self.biz.service.ai;

import cn.hoxise.self.biz.dal.entity.MovieDbBangumiActorDO;
import cn.hoxise.self.biz.dal.entity.MovieDbBangumiCharacterDO;
import cn.hoxise.self.biz.dal.entity.MovieDbBangumiDO;
import cn.hoxise.self.biz.dal.entity.MovieDbBangumiInfoboxDO;
import cn.hoxise.self.biz.service.movie.bangumi.MovieDbBangumiActorService;
import cn.hoxise.self.biz.service.movie.bangumi.MovieDbBangumiCharacterService;
import cn.hoxise.self.biz.service.movie.bangumi.MovieDbBangumiInfoboxService;
import cn.hoxise.self.biz.service.movie.bangumi.MovieDbBangumiService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author hoxise
 * @Description: 向量存储
 * @Date 2025-12-26 下午7:11
 */
@Service
@Slf4j
public class AiVectorStoreServiceImpl implements AiVectorStoreService {

    @Autowired(required = false)
    private VectorStore vectorStore;

    @Resource
    private MovieDbBangumiService movieDbBangumiService;

    @Resource
    private MovieDbBangumiInfoboxService infoboxService;

    @Resource
    private MovieDbBangumiCharacterService characterService;

    @Resource
    private MovieDbBangumiActorService actorService;

    @Value("${nasCloud.pushVectorStore}")
    private boolean pushVectorStore;

    @Override
    @Async
    public void pushVectorStore() {
        if (vectorStore == null){
            log.error("vectorStore未初始化");
            return;
        }
        if (!pushVectorStore){
            log.info("vectorStore未开启推送");
            return;
        }
        log.info("开始推送向量数据...");
        //批次查询
        int batchSize = 10;
        long pageNum = movieDbBangumiService.count() / batchSize;
        log.info("总页数: {}", pageNum+1);

        for (int i = 1; i <= pageNum+1; i++) {
            log.info("开始处理第{}页数据...", i);
            List<MovieDbBangumiDO> list = movieDbBangumiService.list(new Page<>(i, batchSize), Wrappers.lambdaQuery(MovieDbBangumiDO.class));
            List<Long> catalogids = list.stream().map(MovieDbBangumiDO::getCatalogid).toList();
            List<Long> bangumiIds = list.stream().map(MovieDbBangumiDO::getBangumiId).toList();
            //信息框
            List<MovieDbBangumiInfoboxDO> infoboxDOList = infoboxService.list(Wrappers.lambdaQuery(MovieDbBangumiInfoboxDO.class).in(MovieDbBangumiInfoboxDO::getBangumiId, bangumiIds));
            //角色信息
            List<MovieDbBangumiCharacterDO> characterDOList = characterService.list(Wrappers.lambdaQuery(MovieDbBangumiCharacterDO.class).in(MovieDbBangumiCharacterDO::getCatalogid, catalogids));
            //演员、CV信息
            List<String> actorIds = characterDOList.stream().flatMap(chara -> chara.getActors().stream()).distinct().toList();
            List<MovieDbBangumiActorDO> actorDOList = actorService.list(Wrappers.lambdaQuery(MovieDbBangumiActorDO.class).in(MovieDbBangumiActorDO::getActorId,actorIds));

            List<Document> documents = new ArrayList<>();
            list.forEach(f->{
                List<MovieDbBangumiInfoboxDO> infobox = infoboxDOList.stream().filter(info -> info.getBangumiId().equals(f.getBangumiId())).toList();
                List<MovieDbBangumiCharacterDO> characters = characterDOList.stream().filter(character -> character.getCatalogid().equals(f.getCatalogid())).toList();
                List<MovieDbBangumiActorDO> actors = actorDOList.stream().filter(actor -> characters.stream().anyMatch(character -> character.getActors().contains(actor.getActorId().toString()))).toList();
                Document document = buildVectorStore(f, infobox, characters, actors);
                documents.add(document);
            });
            vectorStore.accept(documents);
        }
        log.info("向量数据推送完成");
    }


    /**
     * @description: 构造向量数据
     * @param	movieDb db数据
     * @author: hoxise
     * @date: 2025/12/26 下午8:01
     */
    private Document buildVectorStore(MovieDbBangumiDO movieDb, List<MovieDbBangumiInfoboxDO> infobox, List<MovieDbBangumiCharacterDO> characters, List<MovieDbBangumiActorDO> actors) {

        //处理下防止报错
        movieDb.setTags(movieDb.getTags() == null ? new ArrayList<>() : movieDb.getTags());
        movieDb.setMetaTags(movieDb.getMetaTags() == null ? new ArrayList<>() : movieDb.getMetaTags());

        String characterStr = characters.stream().map(MovieDbBangumiCharacterDO::getName).collect(Collectors.joining(","));
        String actorStr = actors.stream().map(MovieDbBangumiActorDO::getName).collect(Collectors.joining(","));
        //向量文本
        HashMap<String,String> textMap = new HashMap<>();
        textMap.put("id", movieDb.getCatalogid().toString());
        textMap.put("name", movieDb.getMatchingName());
        textMap.put("originName", movieDb.getOriginalName());
        textMap.put("tags", String.join(",", movieDb.getTags()));
        textMap.put("metaTags", String.join(",", movieDb.getMetaTags()));
        textMap.put("platform", movieDb.getPlatform());
        textMap.put("characters", characterStr);
        textMap.put("actor", actorStr);
        textMap.put("releaseYear", movieDb.getReleaseDate()==null?"": movieDb.getReleaseDate().toString().substring(0,4));
        String infoBoxStr = infobox.stream().map(info -> info.getInfoboxKey() + ":" + info.getInfoboxValue()).collect(Collectors.joining(","));
        textMap.put("infobox",infoBoxStr);

        String text = StrUtil.format("""
                id: {id}
                名称: {name},
                原始名称: {originName},
                标签: {tags},
                元标签: {metaTags},
                类型: {platform},
                角色: {characters},
                演员或CV: {actor},
                发布日期: {releaseYear},
                其它信息: {infobox}
                """,textMap);

        //元数据
        Map<String, Object> metaMap = Map.of("id", movieDb.getCatalogid()
                                            , "name", movieDb.getMatchingName()
                                            , "originName", movieDb.getOriginalName()
                                            , "tags",String.join(",", movieDb.getTags())
                                            , "metaTags",String.join(",", movieDb.getMetaTags())
                                            , "type", movieDb.getPlatform()
                                            , "charactors", characterStr
                                            , "actors",actorStr
                                            , "releaseYear", movieDb.getReleaseDate()==null?"":movieDb.getReleaseDate().getYear() );

        return new Document(movieDb.getCatalogid().toString(),text,metaMap);

    }


}
