package cn.hoxise.module.movie.service.bangumi;

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
     * @param bangumiId bangumiId
     * @return 章节数据
     * @author hoxise
     * @since 2026/01/14 15:14:32
     */
    List<BangumiDbEpisodeDO> listByBangumiId(Long bangumiId);

    /**
     * 获取章节视图信息
     *
     * @param bangumiId bangumiId
     * @return 章节视图数据
     * @author hoxise
     * @since 2026/01/14 15:14:46
     */
    List<MovieEpisodesVO> listVoByBangumiId(Long bangumiId);


    void removeByBangumiId(Long bangumiId);
}
