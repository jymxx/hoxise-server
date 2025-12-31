package cn.hoxise.self.biz.service.movie.bangumi;

import cn.hoxise.common.base.utils.date.DateUtil;
import cn.hoxise.common.base.utils.file.FileUtils;
import cn.hoxise.common.base.utils.img.ImgUtil;
import cn.hoxise.common.file.api.dto.FileStorageDTO;
import cn.hoxise.common.file.utils.FileStorageUtil;
import cn.hoxise.self.biz.dal.entity.*;
import cn.hoxise.self.biz.pojo.dto.BangumiCharacterResponse;
import cn.hoxise.self.biz.pojo.dto.BangumiEpisodesResponse;
import cn.hoxise.self.biz.pojo.dto.BangumiSearchSubjectReq;
import cn.hoxise.self.biz.pojo.dto.BangumiSearchSubjectResponse;
import cn.hoxise.self.biz.pojo.enums.bangumi.BangumiSubjectTypeEnum;
import cn.hoxise.self.biz.service.movie.MovieCatalogService;
import cn.hoxise.self.biz.service.movie.MovieScanLogService;
import cn.hoxise.self.biz.service.tmdb.MovieDbDO;
import cn.hoxise.self.biz.pojo.constants.MovieConstants;
import cn.hoxise.self.biz.pojo.constants.MovieRedisConstants;
import cn.hoxise.self.biz.service.tmdb.TMDBMulitSearchItem;
import cn.hoxise.self.biz.service.tmdb.TMDBMulitSearchResponse;
import cn.hoxise.self.biz.pojo.enums.MovieStatusEnum;
import cn.hoxise.self.biz.service.tmdb.TMDBMediaTypeEnum;
import cn.hoxise.self.biz.service.tmdb.MovieDbService;
import cn.hoxise.self.biz.service.tmdb.TMDBUtil;
import cn.hoxise.self.biz.utils.BangumiUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @Author hoxise
 * @Description: Bangumi管理实现类
 * @Date 2025-12-22 下午3:47
 */
@Service
@Slf4j
public class MovieBangumiManageServiceImpl implements MovieBangumiManageService {


    @Value("${nasCloud.disk}")
    private String configDisk;

    @Value("${nasCloud.dirs}")
    private String configDirs;

    private final ReentrantLock lock = new ReentrantLock();

    @Resource private MovieScanLogService movieScanLogService;

    @Resource private MovieCatalogService movieCatalogService;

    @Resource private MovieDbService movieDbService;

    @Resource private MovieBangumiManageService movieBangumiManageService;

    @Resource private MovieDbBangumiService movieDbBangumiService;

    @Resource private MovieDbBangumiInfoboxService movieDbBangumiInfoboxService;

    @Resource private MovieDbBangumiCharacterService movieDbBangumiCharacterService;

    @Resource private MovieDbBangumiActorService movieDbBangumiActorService;

    @Resource private MovieDbBangumiEpisodeService movieDbBangumiEpisodeService;


    @Override
    public List<BangumiSearchSubjectResponse.Subject> queryByNameFromBangumi(String name) {
        Assert.notNull(name, "输入关键字不能为空");
        BangumiSearchSubjectReq req = BangumiSearchSubjectReq.builder().keyword(name).build();
        req.setFilter(BangumiSearchSubjectReq.BangumiSearchFilter.builder().type(List.of(BangumiSubjectTypeEnum.ANIME.getCode())).build());
        BangumiSearchSubjectResponse response = BangumiUtil.searchSubjects(req);
        return response.getData();
    }

    @Override
    @Transactional
    @CacheEvict(
            value = MovieRedisConstants.MOVIE_LIBRARY_KEY,
            allEntries = true  // 数据更新后清除整个缓存区域
    )
    public void updateBangumi(Long catalogid, Long bangumiId) {
        Assert.notNull(catalogid, "catalogId不能为空");
        Assert.notNull(bangumiId, "bangumiId不能为空");

        //目录数据
        MovieCatalogDO catalogDO = movieCatalogService.getById(catalogid);
        //DB数据
        BangumiSearchSubjectResponse.Subject subject = BangumiUtil.getSubject(bangumiId.toString());

        //保存DB数据
        saveBangumiWithInfobox(catalogDO,subject);
        //保存角色和CV数据
        apiUpdateCharactersAndActors(bangumiId,catalogid);
        //保存章节信息
        apiUpdateEpisode(bangumiId,catalogid);

    }


//####################### 扫描 ###################

    @Override
    @Transactional
    public void dirScan(boolean scanUpdate){
        if (!lock.tryLock()) {
            log.warn("当前已有扫描任务正在运行，跳过本次扫描");
            return;
        }
        MovieScanLogDO logDO = new MovieScanLogDO();
        logDO.setStartScanTime(LocalDateTime.now());
        try {
            Assert.isFalse(configDisk.isBlank() || configDirs.isBlank(),"配置异常,盘符与目录不能为空");

            List<String> dirs = Arrays.stream(configDirs.split(",")).toList();
            dirs.forEach(dir -> Assert.isTrue(FileUtil.exist(configDisk.concat(dir)),"目录不存在:" + dir));

            dirs.forEach(f->startScan(f,scanUpdate));
        }catch (Exception e){
            logDO.setLog(e.toString());
            log.error("扫描异常:",e);
        }finally {
            logDO.setEndScanTime(LocalDateTime.now());
            movieScanLogService.save(logDO);
            log.info("---扫描完成");
            lock.unlock();
        }

    }

    /**
     * @description: 按配置路径开始扫描
     * @author: hoxise
     * @date: 2025/8/12 上午6:02
     */
    private void startScan(String dir,boolean scanUpdate) {
        log.info("---开始扫描目录:" + dir);
        // 扫描目录
        String fullPath = configDisk.concat(dir);
        List<File> directory = Arrays.stream(FileUtil.ls(fullPath)).filter(File::isDirectory).toList();
        List<String> realDirNames = directory.stream().map(File::getName).toList();

        // 查询数据库中已有数据
        List<MovieCatalogDO> dbMovie = movieCatalogService.list(Wrappers.lambdaQuery(MovieCatalogDO.class)
                .eq(MovieCatalogDO::getDirectory, dir));

        //筛选出过期数据(被删除、改名等原因导致数据库里的记录实际不存在) 将这些数据的状态更新
        List<Long> expireDataId = dbMovie.stream().filter(anime -> !realDirNames.contains(anime.getName())).map(MovieCatalogDO::getId).toList();
        if (!expireDataId.isEmpty()){
            movieCatalogService.removeBatchByIds(expireDataId);
        }

        Map<String, MovieCatalogDO> dbNameMapScan = dbMovie.stream().filter(anime -> !expireDataId.contains(anime.getId()))
                .collect(Collectors.toMap(MovieCatalogDO::getName, f->f));
        List<MovieCatalogDO> saveList = new ArrayList<>();
        List<MovieCatalogDO> updateList = new ArrayList<>();
        //新增与更新
        directory.forEach(file -> {
            String name = file.getName();
            if (!dbNameMapScan.containsKey(name)){
                //新增
                MovieCatalogDO build = MovieCatalogDO.builder().path(fullPath).name(name).directory(dir)
                        .lastScanTime(LocalDateTime.now()).lastModifyTime(DateUtil.ofInstant(file.lastModified())) //当前没必要递归扫一遍，更新时只判断扫描时间和实际文件的修改时间
                        .totalSize(new BigDecimal(FileUtil.size(file)).divide(new BigDecimal(1024 * 1024 * 1024), 2, RoundingMode.HALF_UP).doubleValue())//转GB
                        .status(MovieStatusEnum.NORMAL).build();
                saveList.add(build);
            }else{
                //更新
                if (scanUpdate){
                    MovieCatalogDO updateMovie = dbNameMapScan.get(name);
                    updateMovie.setLastScanTime(LocalDateTime.now());
                    updateMovie.setLastModifyTime(DateUtil.ofInstant(FileUtils.loopLastModifiedTime(file)));//递归出最后更新时间
                    updateMovie.setTotalSize(new BigDecimal(FileUtil.size(file)).divide(new BigDecimal(1024 * 1024 * 1024), 2, RoundingMode.HALF_UP).doubleValue());//转GB
                    updateList.add(updateMovie);
                }
            }
        });
        movieCatalogService.saveBatch(saveList);
        movieCatalogService.saveOrUpdateBatch(updateList);

    }

    @Override
    public void allMatchingBangumi(boolean isAllUpdate){
        List<MovieCatalogDO> movieCatalogs;
        if (!isAllUpdate){
            //待匹配的数据,筛选出MovieDb里没有的
            movieCatalogs = movieCatalogService.listNotIn(movieDbBangumiService.getCatalogIdList());
        }else{
            //全量更新
            movieCatalogs = movieCatalogService.list();
        }

        List<String> animeDirectory = List.of("动漫","动漫电影");//动漫目录名称
        //动漫请求条件
        BangumiSearchSubjectReq animeReq = new BangumiSearchSubjectReq();
        animeReq.setFilter(BangumiSearchSubjectReq.BangumiSearchFilter.builder().type(List.of(BangumiSubjectTypeEnum.ANIME.getCode())).build());

        //三次元请求条件
        BangumiSearchSubjectReq realReq = new BangumiSearchSubjectReq();
        realReq.setFilter(BangumiSearchSubjectReq.BangumiSearchFilter.builder().type(List.of(BangumiSubjectTypeEnum.REAL.getCode())).build());

        movieCatalogs.forEach(movie -> {
            log.info("开始匹配: %s".formatted(movie.getName()));
            //清理字符
            String keyword = MovieConstants.MOVIE_CLEAN_PATTERN.matcher(movie.getName()).replaceAll("");
            animeReq.setKeyword(keyword);
            realReq.setKeyword(keyword);

            //获取数据
            BangumiSearchSubjectResponse response;
            if (animeDirectory.contains(movie.getDirectory())){
                response = BangumiUtil.searchSubjects(animeReq);
            }else{
                response = BangumiUtil.searchSubjects(realReq);
            }

            List<BangumiSearchSubjectResponse.Subject> subjects = response.getData();
            if (subjects !=null && !subjects.isEmpty()){
                //默认第一个最匹配的数据
                BangumiSearchSubjectResponse.Subject subject = subjects.get(0);
                //事务保存
                movieBangumiManageService.saveBangumiWithInfobox(movie,subject);
            }
        });
    }

    @Override
    @Transactional
    public void saveBangumiWithInfobox(MovieCatalogDO catalog, BangumiSearchSubjectResponse.Subject subject) {
        //项目数据
        MovieDbBangumiDO build = buildDbBangumiDO(catalog, subject);
        //信息框数据
        List<MovieDbBangumiInfoboxDO> infoboxList = buildInfoBoxDO(catalog,subject);

        //已有就更新 不删除
        MovieDbBangumiDO dbOne = movieDbBangumiService.getByCatalogId(catalog.getId());
        if (dbOne != null){
            build.setId(dbOne.getId());
        }
        FileStorageUtil.deleteFile(build.getPosterUrl());//删除封面图片
        movieDbBangumiService.saveOrUpdate(build);
        movieDbBangumiInfoboxService.removeByCatalogId(catalog.getId());
        movieDbBangumiInfoboxService.saveBatch(infoboxList);
    }

    @Override
    public void matchCharacters() {
        List<MovieDbBangumiDO> list = movieDbBangumiService.list();
        list.forEach(one -> {
            log.info("开始匹配角色: %s".formatted(one.getMatchingName()));
            movieBangumiManageService.apiUpdateCharactersAndActors(one.getBangumiId(),one.getCatalogid());
        });
    }

    @Transactional
    @Override
    public void apiUpdateCharactersAndActors(Long bangumiId,Long catalogid) {
        //已有就跳过
        long count = movieDbBangumiCharacterService.count(Wrappers.lambdaQuery(MovieDbBangumiCharacterDO.class)
                .eq(MovieDbBangumiCharacterDO::getCatalogid, catalogid));
        if (count > 0){
            log.warn("--角色已存在,跳过: %s".formatted(catalogid));
            return;
        }

        // API获取角色信息
        List<BangumiCharacterResponse.CharacterInfo> characters = BangumiUtil.getCharacter(bangumiId.toString());
        //角色DO
        List<MovieDbBangumiCharacterDO> characterDOS = buildCharacterDO(characters, catalogid);

        //查询已有的CV信息
        List<Long> actors = new ArrayList<>(characterDOS.stream().flatMap(fm -> fm.getActors().stream()).map(Long::valueOf).distinct().toList());
        if (!actors.isEmpty()){
            List<Long> dbActors = movieDbBangumiActorService.listByActorIds(actors).stream().map(MovieDbBangumiActorDO::getActorId).toList();
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
            List<MovieDbBangumiActorDO> actorDOS = buildActorDO(actorList);
            movieDbBangumiActorService.saveBatch(actorDOS);
        }

        //保存
        movieDbBangumiCharacterService.saveBatch(characterDOS);
    }

    @Override
    public void matchEpisode() {
        List<MovieDbBangumiDO> list = movieDbBangumiService.list();
        list.forEach(one -> {
            log.info("开始匹配: %s".formatted(one.getMatchingName()));
            movieBangumiManageService.apiUpdateEpisode(one.getBangumiId(),one.getCatalogid());
        });
    }


    @Transactional
    @Override
    public void apiUpdateEpisode(Long bangumiId,Long catalogid) {
        //已有就跳过
        long count = movieDbBangumiEpisodeService.count(Wrappers.lambdaQuery(MovieDbBangumiEpisodeDO.class)
                .eq(MovieDbBangumiEpisodeDO::getCatalogid, catalogid));
        if (count > 0){
            log.warn("-----跳过，已存在章节数据: %s".formatted(bangumiId));
            return;
        }
        //api获取接口信息
        BangumiEpisodesResponse episodes = BangumiUtil.getEpisodes(bangumiId.toString());
        List<BangumiEpisodesResponse.EpisodeInfo> data = episodes.getData();
        //构建数据并保存
        List<MovieDbBangumiEpisodeDO> saveList = new ArrayList<>();
        data.forEach(episode -> saveList.add(buildEpisodeDO(episode,catalogid)));
        movieDbBangumiEpisodeService.saveBatch(saveList);
    }

    /**
     * @description: 构建数据库的BangumiDO
     * @param	catalogDO 目录DO
     * @param	subject 搜索结果DO
     * @return	MovieDbBangumiDO
     * @author: hoxise
     * @date: 2025/12/23 下午6:50
     */
    private MovieDbBangumiDO buildDbBangumiDO(MovieCatalogDO catalogDO, BangumiSearchSubjectResponse.Subject subject){
        //项目数据
        return MovieDbBangumiDO.builder()
                .catalogid(catalogDO.getId())
                .bangumiId(subject.getId().longValue())
                .matchingName(catalogDO.getName())
                .subjectType(BangumiSubjectTypeEnum.getByCode(subject.getType()))
                .releaseDate(DateUtil.handleDateStr(subject.getDate()))
                .platform(subject.getPlatform())
                .posterUrl(BangumiUtil.handleImgBangumi(subject.getImage()==null?subject.getImages().getLarge():subject.getImage(), String.valueOf(subject.getId())))
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
     * @description: 构建infobox数据
     * @param	subject 搜索结果DO
     * @return	List<MovieDbBangumiInfoboxDO>
     * @author: hoxise
     * @date: 2025/12/23 下午6:50
     */
    private List<MovieDbBangumiInfoboxDO> buildInfoBoxDO(MovieCatalogDO catalog,BangumiSearchSubjectResponse.Subject subject){
        List<MovieDbBangumiInfoboxDO> infoboxList = new ArrayList<>();
        List<BangumiSearchSubjectResponse.InfoboxItem> infobox = subject.getInfobox();
        infobox.forEach(item -> {
            infoboxList.add(MovieDbBangumiInfoboxDO.builder().bangumiId(subject.getId().longValue()).catalogid(catalog.getId())
                    .infoboxKey(item.getKey()).infoboxValue(item.getValue().toString()).build());
        });
        return infoboxList;
    }

    /**
     * @description: 构建角色数据
     * @param	characterInfos 角色信息
     * @param	catalogid 目录id
     * @return	List<MovieDbBangumiCharacterDO>
     * @author: hoxise
     * @date: 2025/12/23 下午6:50
     */
    private List<MovieDbBangumiCharacterDO> buildCharacterDO(List<BangumiCharacterResponse.CharacterInfo> characterInfos, Long catalogid){
        List<MovieDbBangumiCharacterDO> returnList = new ArrayList<>();

        characterInfos.forEach(f-> {
            MovieDbBangumiCharacterDO characterOne = MovieDbBangumiCharacterDO.builder()
                    .catalogid(catalogid)
                    .characterId(f.getId().longValue())
                    .name(f.getName())
                    .imgUrl(BangumiUtil.handleImgBangumi(f.getImages().getLarge(), f.getId().toString()))
                    .relation(f.getRelation())
                    .type(f.getType())
                    .actors(f.getActors().stream().map(actor -> actor.getId().toString()).toList())
                    .build();
            returnList.add(characterOne);
        });
        return returnList;
    }
    
    /**
     * @description: 构建演员数据
     * @param	actorInfos 演员信息
     * @return	List<MovieDbBangumiActorDO>
     * @author: hoxise
     * @date: 2025/12/23 下午6:50
     */
    private List<MovieDbBangumiActorDO> buildActorDO(List<BangumiCharacterResponse.ActorInfo> actorInfos){
        List<MovieDbBangumiActorDO> returnList = new ArrayList<>();

        actorInfos.forEach(actor -> {
            MovieDbBangumiActorDO actorDO = MovieDbBangumiActorDO.builder()
                    .actorId(actor.getId().longValue())
                    .name(actor.getName())
                    .career(actor.getCareer())
                    .shortSummary(actor.getShort_summary())
                    .type(actor.getType())
                    .imgUrl(BangumiUtil.handleImgBangumi(actor.getImages().getLarge(), actor.getId().toString()))
                    .build();
            returnList.add(actorDO);
        });
        return returnList;
    }

    /**
     * @return
     * @description: 构建章节数据
     * @param    episode 章节信息
     * @param    catalogid 目录id
     * @author: hoxise
     * @date: 2025/12/23 下午6:50
     */
    private MovieDbBangumiEpisodeDO buildEpisodeDO(BangumiEpisodesResponse.EpisodeInfo episode, Long catalogid){

        return MovieDbBangumiEpisodeDO.builder()
                .catalogid(catalogid)
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


//################################################## TMDB接口 ###################################################################
    /**
     * @Author: hoxise
     * @Description: 匹配TMDB数据
     * @Date: 2025/12/22 下午4:34
     */
    @Override
    @Deprecated
    public void allMatchingTMDB(){

        //待匹配的数据,筛选出MovieDb里没有的
        List<MovieCatalogDO> movieCatalogs = movieCatalogService.listNotIn(movieDbService.getCatalogIdList());

        movieCatalogs.forEach(movie -> {
            log.info("开始匹配: %s".formatted(movie.getName()));
            String keyword = MovieConstants.MOVIE_CLEAN_PATTERN.matcher(movie.getName()).replaceAll("");
            TMDBMulitSearchResponse response = movieBangumiManageService.searchMultiCache(keyword);
            List<TMDBMulitSearchItem> results = response.getResults();
            if (results !=null && !results.isEmpty()){
                //默认选择第一个
                TMDBMulitSearchItem item = results.get(0);
                TMDBMediaTypeEnum mediaType = TMDBMediaTypeEnum.getByName(item.getMediaType());
                MovieDbDO movieDbDO = MovieDbDO.builder()
                        .catalogid(movie.getId())
                        .tmdbId((long) item.getId())
                        .matchingType(item.getMediaType())
                        .overview(item.getOverview())
                        .voteAverage(item.getVoteAverage())
                        .matchingTime(LocalDateTime.now())
                        .build();

                if (mediaType == TMDBMediaTypeEnum.TV){
                    //电视剧/动漫
                    movieDbDO.setMatchingName(item.getName());
                    movieDbDO.setOriginalName(item.getOriginalName());
                    movieDbDO.setReleaseDate(DateUtil.handleDateStr(item.getFirstAirDate()));
                    movieDbDO.setPosterUrl(handleImg(item.getPosterPath(), item.getName() + "_Poster.jpg"));
                    movieDbDO.setBackdropUrl(handleImg(item.getBackdropPath(), item.getName() + "_backdrop.jpg"));
                    movieDbDO.setOriginCountry(item.getOriginCountry()==null?"":item.getOriginCountry().toString());
                }else if (mediaType == TMDBMediaTypeEnum.Movie){
                    //电影/动漫电影
                    movieDbDO.setMatchingName(item.getTitle());
                    movieDbDO.setOriginalName(item.getOriginalTitle());
                    movieDbDO.setReleaseDate(DateUtil.handleDateStr(item.getReleaseDate()));
                    movieDbDO.setPosterUrl(handleImg(item.getPosterPath(), item.getTitle() + "_Poster.jpg"));
                    movieDbDO.setBackdropUrl(handleImg(item.getBackdropPath(), item.getTitle() + "_backdrop.jpg"));
                    movieDbDO.setOriginCountry(item.getOriginalLanguage());
                }
                movieDbService.save(movieDbDO);
            }
        });

    }

    private String handleImg(String url, String fileName){
        FileStorageDTO fileStorageDTO = FileStorageUtil.uploadFile(ImgUtil.downloadImg(url), MovieConstants.TMDB_MINIO_FLODER, fileName);
        return fileStorageDTO.getObjectName();
    }

    @Override
    @Cacheable(cacheNames = MovieRedisConstants.TMDB_SEARCHMULTI_KEY, key = "#keyword")
    public TMDBMulitSearchResponse searchMultiCache(String keyword){
        return TMDBUtil.searchMulti(keyword);
    }



}
