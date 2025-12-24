package cn.hoxise.self.biz.service.movie;

import cn.hoxise.self.biz.controller.movie.vo.MovieEpisodesVO;
import cn.hoxise.self.biz.dal.entity.MovieDbBangumiEpisodeDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Hoxise
* @description 针对表【movie_db_bangumi_episode(Bangumi剧集表)】的数据库操作Service
* @createDate 2025-12-24 02:42:23
*/
public interface MovieDbBangumiEpisodeService extends IService<MovieDbBangumiEpisodeDO> {

    List<MovieDbBangumiEpisodeDO> listByCatalogId(Long catalogId);

    List<MovieEpisodesVO> listVOByCatalogId(Long catalogId);
}
