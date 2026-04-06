package cn.hoxise.module.movie.service;

import cn.hoxise.module.movie.dal.entity.MovieCatalogExtraDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Optional;

/**
 * MovieCatalogExtraService
 *
 * @author Hoxise
 * @since 2026/04/06
 */
public interface MovieCatalogExtraService extends IService<MovieCatalogExtraDO> {

    /**
     * 获取播放地址
     *
     * @param catalogId 目录 ID
     * @return 播放地址
     * @author hoxise
     * @since 2026/04/06
     */
    String getPlayUrl(Long catalogId);

    /**
     * 保存或更新播放地址
     *
     * @param catalogId 目录 ID
     * @param playUrl 播放地址
     * @author hoxise
     * @since 2026/04/06
     */
    void saveOrUpdatePlayUrl(Long catalogId, String playUrl);


}
