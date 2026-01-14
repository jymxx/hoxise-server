package cn.hoxise.self.movie.convert;

import cn.hoxise.self.movie.controller.movie.vo.MovieDetailVO;
import cn.hoxise.self.movie.dal.entity.MovieDbBangumiDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * MovieDbBangumiConvert
 *
 * @author hoxise
 * @since 2026/01/14 14:56:24
 */
@Mapper
public interface MovieDbBangumiConvert {

    MovieDbBangumiConvert INSTANCE = Mappers.getMapper(MovieDbBangumiConvert.class);

    MovieDetailVO convert(MovieDbBangumiDO movieDbBangumiDO);

    List<MovieDetailVO> convert(List<MovieDbBangumiDO> movieDbBangumiDO);
}
