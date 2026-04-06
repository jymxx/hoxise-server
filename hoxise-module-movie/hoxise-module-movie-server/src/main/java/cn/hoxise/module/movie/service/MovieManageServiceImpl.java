package cn.hoxise.module.movie.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hoxise.common.base.exception.ServiceException;
import cn.hoxise.module.movie.controller.movie.dto.MovieScanUploadDTO;
import cn.hoxise.module.movie.dal.entity.BangumiDbDO;
import cn.hoxise.module.movie.dal.entity.MovieCatalogDO;
import cn.hoxise.module.movie.pojo.constants.RedisConstants;
import cn.hoxise.module.movie.enums.movie.MovieStatusEnum;
import cn.hoxise.module.movie.enums.movie.MovieTypeEnum;
import cn.hoxise.module.movie.service.bangumi.BangumiDbService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hoxise
 * @since 2026/2/28 下午10:32
 */
@Service
@Slf4j
public class MovieManageServiceImpl implements MovieManageService{

    @Resource private MovieCatalogService movieCatalogService;

    @Resource private BangumiDbService bangumiDbService;

    @Resource private RedissonClient redissonClient;

    @Override
    @Transactional
    public void scanUpload(MovieScanUploadDTO dto){
        long loginId = StpUtil.getLoginIdAsLong();
        MovieTypeEnum directoryName = dto.getDirectoryName();
        List<MovieScanUploadDTO.Directory> scanDirectories = dto.getScanDirectories();

        // 1. 先查询数据库中当前用户在该目录下的所有数据
        List<MovieCatalogDO> dbMovieCatalogs = movieCatalogService.list(Wrappers.lambdaQuery(MovieCatalogDO.class)
                .eq(MovieCatalogDO::getUserId, loginId)
                .eq(MovieCatalogDO::getDirectory, directoryName.getName()));

        // 2. 将数据库数据转换为 Map 便于匹配 (key: name, value: id)
        Map<String, Long> dbCatalogMap = dbMovieCatalogs.stream().collect(Collectors.toMap(
                MovieCatalogDO::getName,MovieCatalogDO::getId
        ));

        // 3. 准备新增和删除的数据列表
        List<MovieCatalogDO> toSaveList = new ArrayList<>();

        // 4. 遍历扫描数据，筛选出需要新增的数据
        scanDirectories.forEach(directory -> {
            String name = directory.getName();
            // 如果数据库中不存在该名称的数据，则添加到新增列表
            if (!dbCatalogMap.containsKey(name)) {
                toSaveList.add(MovieCatalogDO.builder()
                        .name(name)
                        .directory(directoryName)
                        .path(directory.getPath())
                        .totalSize(directory.getTotalSize())
                        .status(MovieStatusEnum.NORMAL)
                        .userId(loginId)
                        .createTime(LocalDateTime.now())
                        .build());
            } else {
                // 如果已存在，从 DB map 中移除，剩下的就是需要删除的 即数据库还有 但实际不存在
                dbCatalogMap.remove(name);
            }
        });

        // 5. 保存新增数据
        if (!toSaveList.isEmpty()) {
            movieCatalogService.saveBatch(toSaveList);
        }

        // 6. 删除多余的旧数据 (dbCatalogMap 中剩余的就是需要删除的)
        if (!dbCatalogMap.isEmpty() && Boolean.TRUE.equals(dto.getDeleteMissing())) {
            movieCatalogService.removeBatchByIds(dbCatalogMap.values());
        }
    }

    @Override
    public void autoMatch(){
        long loginId = StpUtil.getLoginIdAsLong();
        // 获取分布式锁
        String rateLimitKey = RedisConstants.MOVIE_AUTO_MATCH_RATE_LIMIT_KEY + "::" + loginId;
        RRateLimiter lock = redissonClient.getRateLimiter(rateLimitKey);
        //每24小时三次
        lock.trySetRate(RateType.OVERALL, 3, Duration.ofHours(24));
        if (lock.tryAcquire()){
            // 发送 MQ 消息，异步执行自动匹配
            // mq删了 这里可能改改定时任务什么的
            matchDb(loginId);
        }else{
            throw new ServiceException("今日自动匹配请求上限.");
        }
    }

    /**
     * 简单匹配数据库DB
     *
     * @param loginId 登录用户
     * @author hoxise
     * @since 2026/03/06 23:13:54
     */
    @Override
    public void matchDb(Long loginId){
        // 1. 查询当前用户的所有 catalog 数据（排除已经匹配的）
        List<MovieCatalogDO> catalogs = movieCatalogService.list(Wrappers.lambdaQuery(MovieCatalogDO.class)
                .eq(MovieCatalogDO::getUserId, loginId)
                .isNull(MovieCatalogDO::getBangumiId)
                .last("LIMIT 1000"));

        if (catalogs.isEmpty()) {
            return;
        }

        // 2. 遍历 catalog，逐个在数据库中查询匹配
        List<MovieCatalogDO> toUpdateList = new ArrayList<>();

        for (MovieCatalogDO catalog : catalogs) {
            String catalogName = catalog.getName();
            // 在 BangumiDb 中查询名称完全匹配的记录
            BangumiDbDO matchedDb = bangumiDbService.getOne(Wrappers.lambdaQuery(BangumiDbDO.class)
                    .select(BangumiDbDO::getId, BangumiDbDO::getBangumiId)
                    .and(wrapper -> wrapper
                            .eq(BangumiDbDO::getNameCn, catalogName)
                            .or()
                            .eq(BangumiDbDO::getOriginalName, catalogName)
                    )
                    .last("LIMIT 1"));

            if (matchedDb != null) {
                catalog.setBangumiId(matchedDb.getBangumiId());
                catalog.setMatchingTime(LocalDateTime.now());
                toUpdateList.add(catalog);
            }
        }
        // 3. 批量更新匹配的数据
        if (!toUpdateList.isEmpty()) {
            movieCatalogService.updateBatchById(toUpdateList);
        }
    }

//    @Override
//    public void allMatchingBangumi(boolean isAllUpdate){
//        List<MovieCatalogDO> movieCatalogs;
//        if (!isAllUpdate){
//            //待匹配的数据,筛选出MovieDb里没有的
//            movieCatalogs = movieCatalogService.listNotIn(bangumiDbService.getCatalogIdList());
//        }else{
//            //全量更新
//            movieCatalogs = movieCatalogService.list();
//        }
//
//        List<String> animeDirectory = List.of("动漫","动漫电影");//动漫目录名称
//        //动漫请求条件
//        BangumiSearchSubjectReq animeReq = new BangumiSearchSubjectReq();
//        animeReq.setFilter(BangumiSearchSubjectReq.BangumiSearchFilter.builder().type(List.of(BangumiSubjectTypeEnum.ANIME.getCode())).build());
//
//        //三次元请求条件
//        BangumiSearchSubjectReq realReq = new BangumiSearchSubjectReq();
//        realReq.setFilter(BangumiSearchSubjectReq.BangumiSearchFilter.builder().type(List.of(BangumiSubjectTypeEnum.REAL.getCode())).build());
//
//        movieCatalogs.forEach(movie -> {
//            log.info("开始匹配: %s".formatted(movie.getName()));
//            //清理字符
//            String keyword = MovieConstants.MOVIE_CLEAN_PATTERN.matcher(movie.getName()).replaceAll("");
//            animeReq.setKeyword(keyword);
//            realReq.setKeyword(keyword);
//
//            //获取数据
//            BangumiSearchSubjectResponse response;
//            if (animeDirectory.contains(movie.getDirectory())){
//                response = BangumiUtil.searchSubjects(animeReq);
//            }else{
//                response = BangumiUtil.searchSubjects(realReq);
//            }
//
//            List<BangumiSearchSubjectResponse.Subject> subjects = response.getData();
//            if (subjects !=null && !subjects.isEmpty()){
//                //默认第一个最匹配的数据
//                BangumiSearchSubjectResponse.Subject subject = subjects.getFirst();
//                //事务保存
//                bangumiManageService.saveBangumiWithInfobox(movie,subject);
//            }
//        });
//    }

}
