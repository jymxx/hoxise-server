package cn.hoxise.module.movie.service.movie.bangumi;

import cn.hoxise.module.movie.controller.movie.vo.MovieEpisodesVO;
import cn.hoxise.module.movie.dal.entity.BangumiDbEpisodeDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * MovieDbBangumiEpisodeService
 *
 * @author Hoxise
 * @since 2026/01/14 15:14:28
 */
public interface BangumiDbEpisodeService extends IService<BangumiDbEpisodeDO> {

    /**
     * listByCatalogId
     *
     * @param catalogId 目录id
     * @return 章节数据
     * @author hoxise
     * @since 2026/01/14 15:14:32
     */
    List<BangumiDbEpisodeDO> listByCatalogId(Long catalogId);

    /**
     * 获取章节视图信息
     *
     * @param catalogId 目录id
     * @return
     * @author hoxise
     * @since 2026/01/14 15:14:46
     */
    List<MovieEpisodesVO> listVoByCatalogId(Long catalogId);
}
