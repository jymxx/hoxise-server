package cn.hoxise.self.biz.service.movie;

import cn.hoxise.common.base.utils.date.DateUtil;
import cn.hoxise.common.base.utils.file.FileUtils;
import cn.hoxise.common.base.utils.img.ImgUtil;
import cn.hoxise.common.file.api.dto.FileStorageDTO;
import cn.hoxise.common.file.utils.FileStorageUtil;
import cn.hoxise.self.biz.controller.movie.vo.MovieCharactersVO;
import cn.hoxise.self.biz.dal.entity.*;
import cn.hoxise.self.biz.pojo.dto.BangumiCharacterResponse;
import cn.hoxise.self.biz.pojo.dto.BangumiEpisodesResponse;
import cn.hoxise.self.biz.pojo.dto.BangumiSearchSubjectReq;
import cn.hoxise.self.biz.pojo.dto.BangumiSearchSubjectResponse;
import cn.hoxise.self.biz.pojo.enums.BangumiSubjectTypeEnum;
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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @Author hoxise
 * @Description: 影视管理实现类
 * @Date 2025-12-22 下午3:47
 */
@Service
@Slf4j
public class MovieManageServiceImpl implements MovieManageService{


    @Value("${nasCloud.disk}")
    private String configDisk;

    @Value("${nasCloud.dirs}")
    private String configDirs;

    private final ReentrantLock lock = new ReentrantLock();

    @Resource private MovieScanLogService movieScanLogService;

    @Resource private MovieCatalogService movieCatalogService;

    @Resource private MovieDbService movieDbService;

    @Resource private MovieManageService movieManageService;

    @Resource private MovieDbBangumiService movieDbBangumiService;

    @Resource private MovieDbBangumiInfoboxService movieDbBangumiInfoboxService;

    @Resource private MovieDbBangumiCharacterService movieDbBangumiCharacterService;

    @Resource private MovieDbBangumiActorService movieDbBangumiActorService;

    @Resource private MovieDbBangumiEpisodeService movieDbBangumiEpisodeService;

    /**
     * @description: 扫描目录
     * @author: hoxise
     * @date: 2025/8/12 上午5:59
     */
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

    /**
     * @Author: hoxise
     * @Params: [isAllUpdate] 是否全量更新 false则只新增
     * @Description: 匹配Bangumi数据
     * @Date: 2025/12/22 下午4:34
     */
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

                //项目数据
                MovieDbBangumiDO build = MovieDbBangumiDO.builder()
                        .catalogid(movie.getId())
                        .bangumiId(subject.getId().longValue())
                        .matchingName(movie.getName())
                        .subjectType(BangumiSubjectTypeEnum.getByCode(subject.getType()))
                        .releaseDate(handleDate(subject.getDate()))
                        .platform(subject.getPlatform())
                        .posterUrl(BangumiUtil.handleImgBangumi(subject.getImage(),keyword))
                        .summary(subject.getSummary())
                        .originalName(subject.getName())
                        .nameCn(subject.getName_cn())
                        .tags(subject.getTags().stream()
                                .sorted(Comparator.comparingLong(BangumiSearchSubjectResponse.Tag::getCount).reversed())
                                .filter(tag -> tag.getCount() > 10)//筛选出数量大于10的标签
                                .map(BangumiSearchSubjectResponse.Tag::getName).toList()
                        ).rating(subject.getRating().getScore())
                        .metaTags(subject.getMeta_tags())
                        .series(subject.getSeries())
                        .eps(subject.getEps())
                        .volumes(subject.getVolumes())
                        .totalEpisodes(subject.getTotal_episodes())
                        .matchingTime(LocalDateTime.now())
                        .build();
                //信息框数据
                List<MovieDbBangumiInfoboxDO> infoboxList = new ArrayList<>();
                List<BangumiSearchSubjectResponse.InfoboxItem> infobox = subject.getInfobox();
                infobox.forEach(item -> {
                        infoboxList.add(MovieDbBangumiInfoboxDO.builder().bangumiId(subject.getId().longValue())
                                .infoboxKey(item.getKey()).infoboxValue(item.getValue().toString()).build());
                });
                //事务保存
                movieManageService.saveBangumiWithInfobox(build,infoboxList);
            }
        });
    }

    @Override
    @Transactional
    public void saveBangumiWithInfobox(MovieDbBangumiDO bangumi, List<MovieDbBangumiInfoboxDO> infoboxList) {
        //已有就更新 不删除
        MovieDbBangumiDO dbOne = movieDbBangumiService.getByCatalogId(bangumi.getCatalogid());
        if (dbOne != null){
            bangumi.setId(dbOne.getId());
        }
        movieDbBangumiService.saveOrUpdate(bangumi);
        movieDbBangumiInfoboxService.removeByBangumiId(bangumi.getBangumiId());
        movieDbBangumiInfoboxService.saveBatch(infoboxList);
    }


    /**
     * @description: 匹配角色 和 CV
     * @author: hoxise
     * @date: 2025/12/23 下午6:50
     */
    @Override
    public void matchCharacters() {
        List<MovieDbBangumiDO> list = movieDbBangumiService.list();
        list.forEach(one -> {
            List<MovieCharactersVO> characters = movieDbBangumiCharacterService.getCharacters(one.getCatalogid());
            if (characters == null || characters.isEmpty()){
                log.info("开始匹配角色: %s".formatted(one.getMatchingName()));
                movieManageService.apiUpdateCharacters(one.getBangumiId(),one.getCatalogid());
            }else{
                log.warn("--跳过角色已存在的条目: %s".formatted(one.getMatchingName()));
            }
        });

    }

    /**
     * @description: Api获取角色信息并把没有的数据保存进数据库
     * @param	bangumiId 条目id
     * @author: hoxise
     * @date: 2025/12/23 下午6:50
     */
    @Transactional
    @Override
    public void apiUpdateCharacters(Long bangumiId,Long catalogid) {
        // API获取角色信息
        List<BangumiCharacterResponse.CharacterInfo> characters = BangumiUtil.getCharacter(bangumiId.toString());

        characters.forEach(f->{
            //查询是否已有该角色数据 没有就构建一个
            MovieDbBangumiCharacterDO characterOne = movieDbBangumiCharacterService.getByCharacterId(f.getId().longValue());
            if (characterOne == null){
                characterOne=MovieDbBangumiCharacterDO.builder()
                        .catalogid(catalogid)
                        .characterId(f.getId().longValue())
                        .name(f.getName())
                        .imgUrl(BangumiUtil.handleImgBangumi(f.getImages().getLarge(),f.getId().toString()))
                        .relation(f.getRelation())
                        .type(f.getType())
                        .actors(f.getActors().stream().map(actor -> actor.getId().toString()).toList())
                        .build();
                movieDbBangumiCharacterService.save(characterOne);
            }

            //处理演员信息
            f.getActors().forEach(actor -> {
                //同样 先检查是否有信息 没有就新增
                MovieDbBangumiActorDO actorOne = movieDbBangumiActorService.getByActorId(actor.getId().longValue());
                if (actorOne == null){
                    actorOne = MovieDbBangumiActorDO.builder()
                            .actorId(actor.getId().longValue())
                            .name(actor.getName())
                            .career(actor.getCareer())
                            .shortSummary(actor.getShort_summary())
                            .type(actor.getType())
                            .imgUrl(BangumiUtil.handleImgBangumi(actor.getImages().getLarge(), actor.getId().toString()))
                            .build();
                    movieDbBangumiActorService.save(actorOne);
                }
            });

        });
    }

    @Override
    public void matchEpisode() {
        List<MovieDbBangumiDO> list = movieDbBangumiService.list();
        list.forEach(one -> {
            log.info("开始匹配: %s".formatted(one.getMatchingName()));
            movieManageService.apiUpdateEpisode(one.getBangumiId(),one.getCatalogid());
        });
    }

    /**
     * @description: Api获取章节信息并把没有的数据保存进数据库
     * @param	bangumiId 条目id
     * @param	catalogid 目录id
     * @author: hoxise
     * @date: 2025/12/23 下午6:50
     */
    @Transactional
    @Override
    public void apiUpdateEpisode(Long bangumiId,Long catalogid) {
        //已有就跳过
        long count = movieDbBangumiEpisodeService.count(Wrappers.lambdaQuery(MovieDbBangumiEpisodeDO.class)
                .eq(MovieDbBangumiEpisodeDO::getCatalogid, bangumiId));
        if (count > 0){
            log.warn("-----跳过，已存在章节数据: %s".formatted(bangumiId));
            return;
        }

        BangumiEpisodesResponse episodes = BangumiUtil.getEpisodes(bangumiId.toString());
        List<BangumiEpisodesResponse.EpisodeInfo> data = episodes.getData();
        data.forEach(episode -> {
            MovieDbBangumiEpisodeDO build = MovieDbBangumiEpisodeDO.builder()
                    .catalogid(catalogid)
                    .episodeId(episode.getId().longValue())
                    .airdate(handleDate(episode.getAirdate()))
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
            movieDbBangumiEpisodeService.save(build);
        });
    }

    /**
     * @Author: hoxise
     * @Description: 处理时间 防止空值报错
     * @Date: 2025/12/22 下午1:45
     */
    private static LocalDateTime handleDate(String date){
        try{
            return LocalDate.parse(date, DateUtil.DATE_FORMATTER).atStartOfDay();
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
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
            TMDBMulitSearchResponse response = movieManageService.searchMultiCache(keyword);
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
                    movieDbDO.setReleaseDate(handleDate(item.getFirstAirDate()));
                    movieDbDO.setPosterUrl(handleImg(item.getPosterPath(), item.getName() + "_Poster.jpg"));
                    movieDbDO.setBackdropUrl(handleImg(item.getBackdropPath(), item.getName() + "_backdrop.jpg"));
                    movieDbDO.setOriginCountry(item.getOriginCountry()==null?"":item.getOriginCountry().toString());
                }else if (mediaType == TMDBMediaTypeEnum.Movie){
                    //电影/动漫电影
                    movieDbDO.setMatchingName(item.getTitle());
                    movieDbDO.setOriginalName(item.getOriginalTitle());
                    movieDbDO.setReleaseDate(handleDate(item.getReleaseDate()));
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
