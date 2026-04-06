package cn.hoxise.module.movie.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hoxise.module.movie.controller.movie.vo.MovieSimpleVO;
import cn.hoxise.module.movie.dal.entity.MovieFavoriteDO;
import cn.hoxise.module.movie.dal.mapper.MovieFavoriteMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * MovieFavoriteService 实现类
 *
 * @author Hoxise
 * @since 2026/04/06
 */
@Service
public class MovieFavoriteServiceImpl extends ServiceImpl<MovieFavoriteMapper, MovieFavoriteDO>
        implements MovieFavoriteService {

    @Resource
    private MovieCatalogService movieCatalogService;

    @Override
    public void favorite(Long catalogId) {
        Long userId = StpUtil.getLoginIdAsLong();
        long count = this.count(Wrappers.lambdaQuery(MovieFavoriteDO.class)
                .eq(MovieFavoriteDO::getUserId, userId));
        if (count >= 100){
            throw new RuntimeException("收藏数量已达上限");
        }

        // 检查是否已收藏
        boolean exists = this.count(Wrappers.lambdaQuery(MovieFavoriteDO.class)
                .eq(MovieFavoriteDO::getUserId, userId)
                .eq(MovieFavoriteDO::getCatalogId, catalogId)) > 0;

        if (exists) {
            return; // 已收藏则不重复添加
        }

        MovieFavoriteDO favorite = MovieFavoriteDO.builder()
                .userId(userId)
                .catalogId(catalogId)
                .createTime(LocalDateTime.now())
                .build();
        this.save(favorite);
    }

    @Override
    public void unfavorite(Long catalogId) {
        Long userId = StpUtil.getLoginIdAsLong();
        this.remove(Wrappers.lambdaQuery(MovieFavoriteDO.class)
                .eq(MovieFavoriteDO::getUserId, userId)
                .eq(MovieFavoriteDO::getCatalogId, catalogId));
    }

    @Override
    public boolean isFavorited(Long catalogId) {
        Long userId = StpUtil.getLoginIdAsLong();
        return this.count(Wrappers.lambdaQuery(MovieFavoriteDO.class)
                .eq(MovieFavoriteDO::getUserId, userId)
                .eq(MovieFavoriteDO::getCatalogId, catalogId)) > 0;
    }

    @Override
    public List<Long> getFavoriteCatalogIds() {
        Long userId = StpUtil.getLoginIdAsLong();
        List<MovieFavoriteDO> favorites = this.list(Wrappers.lambdaQuery(MovieFavoriteDO.class)
                .eq(MovieFavoriteDO::getUserId, userId));
        return favorites.stream()
                .map(MovieFavoriteDO::getCatalogId)
                .collect(Collectors.toList());
    }

}
