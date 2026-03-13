package cn.hoxise.module.ai.service.movie;

import cn.hoxise.module.movie.api.BangumiDbActorApi;
import cn.hoxise.module.movie.api.BangumiDbApi;
import cn.hoxise.module.movie.api.BangumiDbCharacterApi;
import cn.hoxise.module.movie.api.BangumiDbInfoboxApi;
import cn.hoxise.module.movie.api.dto.BangumiDbActorRespDTO;
import cn.hoxise.module.movie.api.dto.BangumiDbCharacterRespDTO;
import cn.hoxise.module.movie.api.dto.BangumiDbRespDTO;
import cn.hoxise.module.movie.api.dto.BangumiDbInfoboxRespDTO;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 向量存储
 *
 * @author hoxise
 * @since 2026/01/14 14:53:22
 */
@Service
@Slf4j
public class AiMovieVectorStoreServiceImpl implements AiMovieVectorStoreService {

    @Resource
    private VectorStore vectorStore;

    @Resource
    private BangumiDbApi bangumiDbApi;

    @Resource
    private BangumiDbInfoboxApi bangumiDbInfoboxApi;

    @Resource
    private BangumiDbCharacterApi bangumiDbCharacterApi;

    @Resource
    private BangumiDbActorApi bangumiDbActorApi;

    @Override
    @Async
    public void pushVectorStore() {
        if (vectorStore == null){
            log.error("vectorStore未初始化");
            return;
        }
        log.info("开始推送向量数据...");

        //批次查询
        int batchSize = 10;
        long pageNum = bangumiDbApi.count().getCheckedData() / batchSize;
        log.info("总页数: {}", pageNum+1);

        for (int i = 1; i <= 2; i++) {
            log.info("开始处理第{}页数据...", i);
            List<BangumiDbRespDTO> list = bangumiDbApi.page(i, batchSize).getCheckedData().getList();
            if (list.isEmpty()){
                break;
            }
            List<Long> catalogIds = list.stream().map(BangumiDbRespDTO::getCatalogId).toList();
            //信息框
            List<BangumiDbInfoboxRespDTO> infoboxDOList = bangumiDbInfoboxApi.list(catalogIds).getCheckedData();
            //角色信息
            List<BangumiDbCharacterRespDTO> characterDOList = bangumiDbCharacterApi.list(catalogIds).getCheckedData();
            //演员、CV信息
            List<Long> actorIds = characterDOList.stream().flatMap(chara -> chara.getActors().stream()).distinct().map(Long::valueOf).toList();
            List<BangumiDbActorRespDTO> actorDOList = bangumiDbActorApi.list(actorIds).getCheckedData();

            List<Document> documents = new ArrayList<>();
            list.forEach(f->{
                List<BangumiDbInfoboxRespDTO> infobox = infoboxDOList.stream().filter(info -> info.getBangumiId().equals(f.getBangumiId())).toList();
                List<BangumiDbCharacterRespDTO> characters = characterDOList.stream().filter(character -> character.getCatalogId().equals(f.getCatalogId())).toList();
                List<BangumiDbActorRespDTO> actors = actorDOList.stream().filter(actor -> characters.stream().anyMatch(character -> character.getActors().contains(actor.getActorId().toString()))).toList();
                //构造单个向量数据
                Document document = buildVectorStore(f, infobox, characters, actors);
                documents.add(document);
            });
            vectorStore.accept(documents);
        }
        log.info("向量数据推送完成");
    }


    /**
     * 构造向量数据
     *
     * @param movieDb    db数据
     * @param infobox db关联的infobox
     * @param characters db关联的角色
     * @param actors db关联的CV/演员
     * @return  Document
     * @author hoxise
     * @since 2026/01/14 14:53:31
     */
    private Document buildVectorStore(BangumiDbRespDTO movieDb, List<BangumiDbInfoboxRespDTO> infobox, List<BangumiDbCharacterRespDTO> characters, List<BangumiDbActorRespDTO> actors) {

        //处理下防止报错
        movieDb.setTags(movieDb.getTags() == null ? new ArrayList<>() : movieDb.getTags());
        movieDb.setMetaTags(movieDb.getMetaTags() == null ? new ArrayList<>() : movieDb.getMetaTags());

        String characterStr = characters.stream().map(BangumiDbCharacterRespDTO::getName).collect(Collectors.joining(","));
        String actorStr = actors.stream().map(BangumiDbActorRespDTO::getName).collect(Collectors.joining(","));
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
        Map<String, Object> metaMap = Map.of("userid"
        );

        return new Document(movieDb.getCatalogid().toString(),text,metaMap);

    }


}
