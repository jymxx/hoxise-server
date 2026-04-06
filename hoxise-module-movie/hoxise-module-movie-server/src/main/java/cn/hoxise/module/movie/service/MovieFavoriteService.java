package cn.hoxise.module.movie.service;

import cn.hoxise.module.movie.controller.movie.vo.MovieSimpleVO;
import cn.hoxise.module.movie.dal.entity.MovieFavoriteDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * MovieFavoriteService
 *
 * @author Hoxise
 * @since 2026/04/06
 */
public interface MovieFavoriteService extends IService<MovieFavoriteDO> {

    /**
     * 收藏
     *
     * @param catalogId 目录 ID
     * @author hoxise
     * @since 2026/04/06
     */
    void favorite(Long catalogId);

    /**
     * 取消收藏
     *
     * @param catalogId 目录 ID
     * @author hoxise
     * @since 2026/04/06
     */
    void unfavorite(Long catalogId);

    /**
     * 检查是否已收藏
     *
     * @param catalogId 目录 ID
     * @return true-已收藏 false-未收藏
     * @author hoxise
     * @since 2026/04/06
     */
    boolean isFavorited(Long catalogId);

    /**
     * 获取用户收藏的目录 ID 列表
     *
     * @return 目录 ID 列表
     * @author hoxise
     * @since 2026/04/06
     */
    List<Long> getFavoriteCatalogIds();

}
