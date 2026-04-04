package cn.hoxise.module.movie.service;

import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hoxise.common.base.pojo.PageResult;
import cn.hoxise.common.redis.utils.RedisUtil;
import cn.hoxise.module.movie.controller.movie.dto.MovieLibraryQueryDTO;
import cn.hoxise.module.movie.controller.movie.dto.MovieSimpleQueryDTO;
import cn.hoxise.module.movie.controller.movie.dto.MovieUpdateDbDTO;
import cn.hoxise.module.movie.controller.movie.vo.MovieSimpleVO;
import cn.hoxise.module.movie.controller.movie.vo.MovieStatVO;
import cn.hoxise.module.movie.convert.MovieCatalogConvert;
import cn.hoxise.module.movie.dal.entity.*;
import cn.hoxise.module.movie.enums.movie.MovieTypeEnum;
import cn.hoxise.module.movie.service.bangumi.BangumiDbService;
import cn.hoxise.module.movie.pojo.constants.MovieConstants;
import cn.hoxise.module.movie.pojo.constants.RedisConstants;
import cn.hoxise.module.movie.service.bangumi.BangumiManageService;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hoxise.module.movie.dal.mapper.MovieCatalogMapper;
import com.google.gson.JsonObject;
import jakarta.annotation.Resource;
import jodd.util.StringUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 影视目录实现
 *
 * @author Hoxise
 * @since 2026/01/14 15:23:19
 */
@Service
public class MovieCatalogServiceImpl extends ServiceImpl<MovieCatalogMapper, MovieCatalogDO>
    implements MovieCatalogService{

    @Resource private BangumiManageService bangumiManageService;

    @Resource private BangumiDbService bangumiDbService;

    @Resource private RedisUtil redisUtil;

    @Override
    public Page<MovieCatalogDO> page(MovieSimpleQueryDTO queryDTO){
        return baseMapper.pageList(queryDTO);
    }

    @Override
    public PageResult<MovieSimpleVO> listPageContainDb(MovieSimpleQueryDTO queryDTO) {
        //目录数据
        Page<MovieCatalogDO> page = page(queryDTO);
        List<MovieCatalogDO> records = page.getRecords();
        List<MovieSimpleVO> convert = MovieCatalogConvert.INSTANCE.convert(records);
        //追加db数据
        addDbData(convert);
        return new PageResult<>(convert, page.getTotal());
    }

    @Override
    @Cacheable(cacheNames = RedisConstants.MOVIE_LIBRARY_KEY,
            key = "{ #queryDTO.userid, #queryDTO.directory, #queryDTO.pageNum}")
    public PageResult<MovieSimpleVO> libraryDbCache(MovieLibraryQueryDTO queryDTO) {
        //默认一次查50条数据
        int batchSize = 50;

        MovieSimpleQueryDTO query = MovieSimpleQueryDTO.builder()
                .userid(queryDTO.getUserid())
                .directory(queryDTO.getDirectory())
                .build();
        query.setPageNum(queryDTO.getPageNum());
        query.setPageSize(batchSize);

        return listPageContainDb(query);
    }

    @Override
    @Cacheable(cacheNames = RedisConstants.MOVIE_STAT_KEY, key = "#userid")
    public MovieStatVO statCount(Long userid){
        MovieStatVO result = new MovieStatVO();
        result.setTotalCount(this.count(Wrappers.lambdaQuery(MovieCatalogDO.class).eq(MovieCatalogDO::getUserid, userid)));
        result.setTotalAnime(this.count(Wrappers.lambdaQuery(MovieCatalogDO.class)
                .eq(MovieCatalogDO::getUserid, userid).eq(MovieCatalogDO::getDirectory,MovieTypeEnum.anime.getName())));
        result.setTotalAnimeMovie(this.count(Wrappers.lambdaQuery(MovieCatalogDO.class)
                .eq(MovieCatalogDO::getUserid, userid).eq(MovieCatalogDO::getDirectory,MovieTypeEnum.animeMovie.getName())));
        result.setTotalOther(this.count(Wrappers.lambdaQuery(MovieCatalogDO.class)
                .eq(MovieCatalogDO::getUserid, userid).notIn(MovieCatalogDO::getDirectory,List.of(MovieTypeEnum.anime.getName(), MovieTypeEnum.animeMovie.getName()))));
        return result;
    }

    @Override
    public List<MovieSimpleVO> randomQuery(Integer limit,Long userid){
        limit = Math.min(limit, 20);

        //todo 改成redis随机
        List<MovieCatalogDO> catalogList = this.list(
                Wrappers.lambdaQuery(MovieCatalogDO.class).eq(MovieCatalogDO::getUserid, userid)
                        .last("order by RAND() LIMIT " + limit)
        );

        List<MovieSimpleVO> result = MovieCatalogConvert.INSTANCE.convert(catalogList);
        addDbData(result);
        return result;
    }

    @Override
    @Cacheable(cacheNames = RedisConstants.MOVIE_LAST_UPDATE_KEY, key = "#userid")
    public List<MovieSimpleVO> lastUpdate(Long userid){
        //最后20条 以ID主键索引排序
        Page<MovieCatalogDO> page = this.page(new Page<>(1, 20)
                , Wrappers.lambdaQuery(MovieCatalogDO.class).eq(MovieCatalogDO::getUserid, userid).orderByDesc(MovieCatalogDO::getId));

        List<MovieSimpleVO> result = MovieCatalogConvert.INSTANCE.convert(page.getRecords());
        // 添加DB数据
        addDbData(result);
        return result;
    }

    /**
     * 向返回视图追加db数据
     *
     * @param simpleVos 返回视图类
     * @author hoxise
     * @since 2026/01/14 15:22:55
     */
    private void addDbData(List<MovieSimpleVO> simpleVos){
        if (simpleVos.isEmpty()){
            return;
        }
        //DB数据
        List<Long> bangumiIds = simpleVos.stream().map(MovieSimpleVO::getBangumiId).toList();
        List<BangumiDbDO> movieDbs = bangumiDbService.listByBangumiIds(bangumiIds);

        Map<Long, BangumiDbDO> movieDbMap = movieDbs.stream().collect(Collectors.toMap(BangumiDbDO::getBangumiId, m -> m));
        //赋值
        simpleVos.forEach(f->{
            BangumiDbDO movieDbDO = movieDbMap.get(f.getBangumiId());
            if (movieDbDO == null){
                return;
            }

            f.setOriginName(movieDbDO.getOriginalName());
            f.setPosterUrl(movieDbDO.getPosterUrl());
            f.setRating(movieDbDO.getRating());
            f.setReleaseYear(movieDbDO.getReleaseDate() == null ? null : movieDbDO.getReleaseDate().getYear());
            f.setPlatform(movieDbDO.getPlatform());
            f.setSubjectType(movieDbDO.getSubjectType());
            f.setMetaTags(movieDbDO.getMetaTags());
        });
    }

    @Override
    @Transactional
    public void updateBangumi(MovieUpdateDbDTO updateDbDTO) {
        //检查权限
        Long userid = checkCatalogPermission(updateDbDTO.getCatalogId());

        //检查是否存在DB且更新
        bangumiManageService.checkExistOrUpdate(updateDbDTO.getBangumiId(),false);

        //挂接数据
        this.update(Wrappers.lambdaUpdate(MovieCatalogDO.class)
                .eq(MovieCatalogDO::getId,updateDbDTO.getCatalogId())
                .set(MovieCatalogDO::getBangumiId,updateDbDTO.getBangumiId()));

        //移除缓存
        removeUserCache(userid);
    }

    @Override
    public void removeAndCache(Long catalogId){
        //检查权限
        Long userid = checkCatalogPermission(catalogId);
        //逻辑删除
        this.removeById(catalogId);
        //移除缓存
        removeUserCache(userid);
    }

    @Override
    public List<Long> getBangumiIdByCatalogId(Collection<Long> catalogIds){
        return this.baseMapper.selectObjs(Wrappers.lambdaQuery(MovieCatalogDO.class)
                        .select(MovieCatalogDO::getBangumiId)
                        .in(catalogIds != null && !catalogIds.isEmpty(), MovieCatalogDO::getId, catalogIds))
                .stream().map(Long.class::cast).toList();
    }

    /**
     * checkCatalogPermission 检查目录权限
     *
     * @param catalogId 目录id
     * @author hoxise
     * @since 2026/02/28 08:24:27
     */
    private Long checkCatalogPermission(Long catalogId){
        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        MovieCatalogDO catalogDO = this.getById(catalogId);
        //只能操作自己的数据
        if (!catalogDO.getUserid().equals(loginIdAsLong)){
            throw new NotPermissionException("禁止操作别人的数据");
        }
        return catalogDO.getUserid();
    }


    /**
     * removeUserCache 移除用户所有影视目录缓存
     *
     * @param userid 用户id
     * @author hoxise
     * @since 2026/02/28 20:53:16
     */
    private void removeUserCache(Long userid){
        // 删除影视库缓存
        String libraryKey = RedisConstants.MOVIE_LIBRARY_KEY + "::" + userid;
        redisUtil.deleteCachePattern(libraryKey + "*");

        // 删除统计缓存
        String statKey = RedisConstants.MOVIE_STAT_KEY + "::" + userid;
        redisUtil.deleteCachePattern(statKey + "*");

        // 删除最后更新缓存
        String lastUpdateKey = RedisConstants.MOVIE_LAST_UPDATE_KEY + "::" + userid;
        redisUtil.deleteCachePattern(lastUpdateKey + "*");
    }
}




