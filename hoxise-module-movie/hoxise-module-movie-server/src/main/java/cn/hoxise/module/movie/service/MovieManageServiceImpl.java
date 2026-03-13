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
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
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

    @Resource
    @Qualifier("autoMatchTaskExecutor")
    private Executor taskExecutor;

    @Override
    @Transactional
    public void scanUpload(MovieScanUploadDTO dto){
        long loginId = StpUtil.getLoginIdAsLong();
        MovieTypeEnum directoryName = dto.getDirectoryName();
        List<MovieScanUploadDTO.Directory> scanDirectories = dto.getScanDirectories();

        // 1. 先查询数据库中当前用户在该目录下的所有数据
        List<MovieCatalogDO> dbMovieCatalogs = movieCatalogService.list(Wrappers.lambdaQuery(MovieCatalogDO.class)
                .eq(MovieCatalogDO::getUserid, loginId)
                .eq(MovieCatalogDO::getDirectory, directoryName));

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
                        .userid(loginId)
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
    public void autoMatch(Long loginId){
        // 获取分布式锁，锁的 key 为用户 ID
        String lockKey = RedisConstants.MOVIE_AUTO_MATCH_LOCK_KEY + "::" + loginId;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // 尝试获取锁，最多等待 0 秒，锁持有时间 10 分钟
            // 等自动释放
            boolean isLocked = lock.tryLock(0, 10, TimeUnit.MINUTES);
            if (!isLocked) {
                log.warn("用户 {} 正在执行其他自动匹配任务，本次请求被拒绝", loginId);
                throw new ServiceException("正在执行其他匹配任务,请10分钟后重试");
            }
            log.info("用户 {} 获得自动匹配锁，开始执行匹配任务", loginId);

            //线程池异步执行
            taskExecutor.execute(() -> {
                matchDb(loginId);
            });

        } catch (InterruptedException e) {
            // 恢复中断状态
            Thread.currentThread().interrupt();
            log.error("用户 {} 自动匹配任务被中断", loginId, e);
        }
    }

    /**
     * 简单匹配数据库DB
     *
     * @param loginId 登录用户
     * @author hoxise
     * @since 2026/03/06 23:13:54
     */
    private void matchDb(Long loginId){
        // 1. 查询当前用户的所有 catalog 数据（排除已经匹配的）
        List<MovieCatalogDO> catalogs = movieCatalogService.list(Wrappers.lambdaQuery(MovieCatalogDO.class)
                .eq(MovieCatalogDO::getUserid, loginId)
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
