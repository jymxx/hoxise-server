package cn.hoxise.module.movie.service.bangumi;

import cn.dev33.satoken.stp.StpUtil;
import cn.hoxise.common.base.utils.date.DateUtil;
import cn.hoxise.common.file.core.client.FileStorageClientFactory;
import cn.hoxise.module.movie.dal.entity.*;
import cn.hoxise.module.movie.pojo.dto.BangumiCharacterResponse;
import cn.hoxise.module.movie.pojo.dto.BangumiEpisodesResponse;
import cn.hoxise.module.movie.pojo.dto.BangumiSearchSubjectReq;
import cn.hoxise.module.movie.pojo.dto.BangumiSearchSubjectResponse;
import cn.hoxise.module.movie.enums.bangumi.BangumiSubjectTypeEnum;
import cn.hoxise.module.movie.utils.BangumiUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Bangumi管理实现类
 *
 * @author hoxise
 * @since 2026/01/14 15:13:21
 */
@Service
@Slf4j
public class BangumiManageServiceImpl implements BangumiManageService {

    @Resource private FileStorageClientFactory fileStorageClientFactory;

    @Resource private BangumiUtil bangumiUtil;

    @Resource private BangumiDbService bangumiDbService;

    @Resource private BangumiDbInfoboxService bangumiDbInfoboxService;

    @Resource private BangumiDbCharacterService bangumiDbCharacterService;

    @Resource private BangumiDbActorService bangumiDbActorService;

    @Resource private BangumiDbEpisodeService bangumiDbEpisodeService;

    @Override
    public List<BangumiSearchSubjectResponse.Subject> queryByNameFromBangumi(String name) {
        Assert.notNull(name, "输入关键字不能为空");
        BangumiSearchSubjectReq req = BangumiSearchSubjectReq.builder().keyword(name).build();
        //只查动漫
        req.setFilter(BangumiSearchSubjectReq.BangumiSearchFilter.builder().type(List.of(BangumiSubjectTypeEnum.ANIME.getCode())).build());
        BangumiSearchSubjectResponse response = BangumiUtil.searchSubjects(req);
        return response.getData();
    }

    @Override
    @Transactional
    public BangumiDbDO checkExistOrUpdate(Long bangumiId, boolean forceUpdate) {
        //只有用户0可以强制更新
        if (0L != StpUtil.getLoginIdAsLong()){
            forceUpdate = false;
        }
        //保存DB和信息框数据
        apiUpdateDbWithInfobox(bangumiId, forceUpdate);
        //保存角色和CV数据
        apiUpdateCharactersAndActors(bangumiId, forceUpdate);
        //保存章节信息
        apiUpdateEpisode(bangumiId, forceUpdate);
        return bangumiDbService.getOne(Wrappers.lambdaQuery(BangumiDbDO.class).eq(BangumiDbDO::getBangumiId, bangumiId));
    }

    @Override
    @Transactional
    public void apiUpdateDbWithInfobox(Long bangumiId, boolean forceUpdate) {

        BangumiDbDO dbOne = bangumiDbService.getByBangumiId(bangumiId);

        //如果不是强制更新且已有数据 就不查询
         if (!forceUpdate && dbOne != null){
             log.warn("--已存在Bangumi DB数据,跳过:bangumiId {%s}".formatted(bangumiId));
             return;
         }

        BangumiSearchSubjectResponse.Subject subject = BangumiUtil.getSubject(bangumiId.toString());
        //项目数据
        BangumiDbDO build = buildDbBangumiDO(subject);
        //信息框数据
        List<BangumiDbInfoboxDO> infoboxList = buildInfoBoxDO(subject);

        //如果是强制更新就将旧数据的ID保留
        if (forceUpdate && dbOne != null){
            build.setId(dbOne.getId());
            fileStorageClientFactory.getDefaultStorage().deleteFile(dbOne.getPosterUrl());//删除旧图片
        }
        bangumiDbService.saveOrUpdate(build);
        bangumiDbInfoboxService.removeByBangumiId(Long.valueOf(subject.getId()));
        bangumiDbInfoboxService.saveBatch(infoboxList);
    }

    @Override
    @Transactional
    public void apiUpdateCharactersAndActors(Long bangumiId, boolean forceUpdate) {
        //已有就跳过
        long count = bangumiDbCharacterService.count(Wrappers.lambdaQuery(BangumiDbCharacterDO.class)
                .eq(BangumiDbCharacterDO::getBangumiId,bangumiId));
        if (!forceUpdate && count > 0){
            log.warn("--角色已同步,跳过:bangumiId {%s}".formatted(bangumiId));
            return;
        }

        // API获取角色信息
        List<BangumiCharacterResponse.CharacterInfo> characters = BangumiUtil.getCharacter(bangumiId.toString());

        //构建待保存角色DO
        List<BangumiDbCharacterDO> characterDoS = buildCharacterDO(characters,bangumiId);

        //查询已有的CV信息
        List<Long> actors = new ArrayList<>(characterDoS.stream().flatMap(fm -> fm.getActors().stream()).map(Long::valueOf).distinct().toList());
        if (!actors.isEmpty()){
            List<Long> dbActors = bangumiDbActorService.listByActorIds(actors).stream().map(BangumiDbActorDO::getActorId).toList();
            actors.removeAll(dbActors);//移除集合中数据库里已有的 即剩下需要新增的
            //过滤出Bangumi里需要新增的CV信息
            List<BangumiCharacterResponse.ActorInfo> actorList = characters.stream()
                    .flatMap(f -> f.getActors().stream())//展开成一个List<ActorInfo>
                    .filter(f -> actors.contains(f.getId().longValue()))
                    .collect(Collectors.toMap(
                            BangumiCharacterResponse.ActorInfo::getId, // 以演员ID为key
                            actor -> actor,         // 以整个对象为value
                            (existing, replacement) -> existing // 如果key重复，保留第一个
                    )).values()
                    .stream()
                    .toList();
            List<BangumiDbActorDO> actorDoS = buildActorDO(actorList);
            //保存CV
            bangumiDbActorService.saveBatch(actorDoS);
        }

        //保存角色
        bangumiDbCharacterService.removeByBangumiId(bangumiId);
        bangumiDbCharacterService.saveBatch(characterDoS);
    }

    @Transactional
    @Override
    public void apiUpdateEpisode(Long bangumiId, boolean forceUpdate) {
        //已有就跳过
        long count = bangumiDbEpisodeService.count(Wrappers.lambdaQuery(BangumiDbEpisodeDO.class)
                .eq(BangumiDbEpisodeDO::getBangumiId,bangumiId));
        if (!forceUpdate && count > 0){
            log.warn("-----跳过，已存在章节数据: %s".formatted(bangumiId));
            return;
        }
        //api获取接口信息
        BangumiEpisodesResponse episodes = BangumiUtil.getEpisodes(bangumiId.toString());
        List<BangumiEpisodesResponse.EpisodeInfo> data = episodes.getData();
        //构建数据
        List<BangumiDbEpisodeDO> saveList = new ArrayList<>();
        data.forEach(episode -> saveList.add(buildEpisodeDO(episode,bangumiId)));
        //保存章节
        bangumiDbEpisodeService.removeByBangumiId(bangumiId);
        bangumiDbEpisodeService.saveBatch(saveList);
    }

    /**
     * 构建数据库的BangumiDO
     *
     * @param subject   搜索结果DO
     * @return MovieDbBangumiDO
     * @author hoxise
     * @since 2026/01/14 15:24:42
     */
    private BangumiDbDO buildDbBangumiDO(BangumiSearchSubjectResponse.Subject subject){
        //项目数据
        return BangumiDbDO.builder()
                .bangumiId(subject.getId().longValue())
                .subjectType(BangumiSubjectTypeEnum.getByCode(subject.getType()))
                .releaseDate(DateUtil.handleDateStr(subject.getDate()))
                .platform(subject.getPlatform())
                .posterUrl(bangumiUtil.handleImgBangumi(subject.getImage()==null?subject.getImages().getLarge():subject.getImage(), String.valueOf(subject.getId())))
                .summary(subject.getSummary())
                .originalName(subject.getName())
                .nameCn(subject.getName_cn())
                .tags(subject.getTags().stream()
                        .sorted(Comparator.comparingLong(BangumiSearchSubjectResponse.Tag::getCount).reversed())
                        .filter(tag -> tag.getCount() > 5)//筛选出数量大于10的标签
                        .map(BangumiSearchSubjectResponse.Tag::getName).toList()
                ).rating(subject.getRating().getScore())
                .metaTags(subject.getMeta_tags())
                .series(subject.getSeries())
                .eps(subject.getEps())
                .volumes(subject.getVolumes())
                .totalEpisodes(subject.getTotal_episodes())
                .matchingTime(LocalDateTime.now())
                .build();
    }

    /**
     * 构建infobox数据
     *
     * @param subject 搜索结果DO
     * @return List<MovieDbBangumiInfoboxDO>
     * @author hoxise
     * @since 2026/01/14 15:24:49
     */
    private List<BangumiDbInfoboxDO> buildInfoBoxDO(BangumiSearchSubjectResponse.Subject subject){
        List<BangumiDbInfoboxDO> infoboxList = new ArrayList<>();
        List<BangumiSearchSubjectResponse.InfoboxItem> infobox = subject.getInfobox();
        infobox.forEach(item -> {
            infoboxList.add(BangumiDbInfoboxDO.builder().bangumiId(subject.getId().longValue())
                    .infoboxKey(item.getKey()).infoboxValue(item.getValue().toString()).build());
        });
        return infoboxList;
    }

    /**
     * 构建角色数据
     *
     * @param characterInfos 角色信息
     * @param bangumiId       bangumiId
     * @return List<MovieDbBangumiCharacterDO>
     * @author hoxise
     * @since 2026/01/14 15:24:59
     */
    private List<BangumiDbCharacterDO> buildCharacterDO(List<BangumiCharacterResponse.CharacterInfo> characterInfos,Long bangumiId){
        List<BangumiDbCharacterDO> returnList = new ArrayList<>();

        characterInfos.forEach(f-> {
            BangumiDbCharacterDO characterOne = BangumiDbCharacterDO.builder()
                    .bangumiId(bangumiId)
                    .characterId(f.getId().longValue())
                    .name(f.getName())
                    .imgUrl(bangumiUtil.handleImgBangumi(f.getImages().getLarge(), f.getId().toString()))
                    .relation(f.getRelation())
                    .type(f.getType())
                    .actors(f.getActors().stream().map(actor -> actor.getId().toString()).toList())
                    .build();
            returnList.add(characterOne);
        });
        return returnList;
    }

    /**
     * 构建演员数据
     *
     * @param actorInfos 演员信息
     * @return List<MovieDbBangumiActorDO>
     * @author hoxise
     * @since 2026/01/14 15:27:05
     */
    private List<BangumiDbActorDO> buildActorDO(List<BangumiCharacterResponse.ActorInfo> actorInfos){
        List<BangumiDbActorDO> returnList = new ArrayList<>();

        actorInfos.forEach(actor -> {
            BangumiDbActorDO actorDO = BangumiDbActorDO.builder()
                    .actorId(actor.getId().longValue())
                    .name(actor.getName())
                    .career(actor.getCareer())
                    .shortSummary(actor.getShort_summary())
                    .type(actor.getType())
                    .imgUrl(bangumiUtil.handleImgBangumi(actor.getImages().getLarge(), actor.getId().toString()))
                    .build();
            returnList.add(actorDO);
        });
        return returnList;
    }

    /**
     * 构建章节数据
     *
     * @param episode   章节信息
     * @param bangumiId bangumiId
     * @return 章节DO
     * @author hoxise
     * @since 2026/01/14 15:27:11
     */
    private BangumiDbEpisodeDO buildEpisodeDO(BangumiEpisodesResponse.EpisodeInfo episode, Long bangumiId){
        return BangumiDbEpisodeDO.builder()
                .bangumiId(bangumiId)
                .episodeId(episode.getId().longValue())
                .airdate(DateUtil.handleDateStr(episode.getAirdate()))
                .name(episode.getName())
                .nameCn(episode.getName_cn())
                .duration(episode.getDuration())
                .description(episode.getDesc())
                .ep(episode.getEp())
                .sort(episode.getSort())
                .comment(episode.getComment())
                .type(episode.getType())
                .disc(episode.getDisc())
                .durationSeconds(episode.getDuration_seconds())
                .build();
    }

}
