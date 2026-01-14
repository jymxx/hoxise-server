package cn.hoxise.self.movie.service.movie.bangumi;

import cn.hoxise.self.movie.controller.movie.vo.MovieEpisodesVO;
import cn.hoxise.self.movie.dal.entity.MovieDbBangumiEpisodeDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * MovieDbBangumiEpisodeService
 *
 * @author Hoxise
 * @since 2026/01/14 15:14:28
 */
public interface MovieDbBangumiEpisodeService extends IService<MovieDbBangumiEpisodeDO> {

    /**
     * listByCatalogId
     *
     * @param catalogId 目录id
     * @return 章节数据
     * @author hoxise
     * @since 2026/01/14 15:14:32
     */
    List<MovieDbBangumiEpisodeDO> listByCatalogId(Long catalogId);

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
