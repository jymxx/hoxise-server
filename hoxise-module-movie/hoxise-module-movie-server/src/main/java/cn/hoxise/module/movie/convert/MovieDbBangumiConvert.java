package cn.hoxise.module.movie.convert;

import cn.hoxise.module.movie.controller.movie.vo.MovieDetailVO;
import cn.hoxise.module.movie.dal.entity.BangumiDbDO;
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

    MovieDetailVO convert(BangumiDbDO bangumiDbDO);

    List<MovieDetailVO> convert(List<BangumiDbDO> bangumiDbDO);
}
