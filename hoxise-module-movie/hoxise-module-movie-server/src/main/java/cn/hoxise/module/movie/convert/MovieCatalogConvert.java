package cn.hoxise.module.movie.convert;

import cn.hoxise.module.movie.controller.movie.vo.MovieSimpleVO;
import cn.hoxise.module.movie.dal.entity.MovieCatalogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * MovieCatalogConvert
 *
 * @author hoxise
 * @since 2026/01/14 14:56:17
 */
@Mapper
public interface MovieCatalogConvert {

    MovieCatalogConvert INSTANCE = Mappers.getMapper(MovieCatalogConvert.class);

    MovieSimpleVO convert(MovieCatalogDO movieCatalogDO);
    List<MovieSimpleVO> convert(List<MovieCatalogDO> movieCatalogDO);
}
