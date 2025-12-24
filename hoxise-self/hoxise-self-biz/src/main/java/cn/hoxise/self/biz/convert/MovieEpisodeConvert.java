package cn.hoxise.self.biz.convert;

import cn.hoxise.self.biz.controller.movie.vo.MovieEpisodesVO;
import cn.hoxise.self.biz.dal.entity.MovieDbBangumiEpisodeDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author hoxise
 * @Description: 章节信息
 * @Date 2025-12-24 下午1:48
 */
@Mapper
public interface MovieEpisodeConvert {

    MovieEpisodeConvert INSTANCE = Mappers.getMapper(MovieEpisodeConvert.class);

    MovieEpisodesVO convert(MovieDbBangumiEpisodeDO movieDbBangumiEpisodeDO);

    List<MovieEpisodesVO> convert(List<MovieDbBangumiEpisodeDO> movieDbBangumiEpisodeDO);


}
