package cn.hoxise.module.movie.convert;

import cn.hoxise.module.movie.controller.movie.vo.MovieEpisodesVO;
import cn.hoxise.module.movie.dal.entity.BangumiDbEpisodeDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * MovieEpisodeConvert
 *
 * @author hoxise
 * @since 2026/01/14 14:56:22
 */
@Mapper
public interface MovieEpisodeConvert {

    MovieEpisodeConvert INSTANCE = Mappers.getMapper(MovieEpisodeConvert.class);

    MovieEpisodesVO convert(BangumiDbEpisodeDO bangumiDbEpisodeDO);

    List<MovieEpisodesVO> convert(List<BangumiDbEpisodeDO> bangumiDbEpisodeDO);


}
