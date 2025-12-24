package cn.hoxise.self.biz.convert;

import cn.hoxise.self.biz.controller.movie.vo.MovieSimpleVO;
import cn.hoxise.self.biz.dal.entity.MovieCatalogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author hoxise
 * @Description: movieCatalog转换类
 * @Date 2025-12-22 下午3:59
 */
@Mapper
public interface MovieCatalogConvert {

    MovieCatalogConvert INSTANCE = Mappers.getMapper(MovieCatalogConvert.class);

    MovieSimpleVO convert(MovieCatalogDO movieCatalogDO);
    List<MovieSimpleVO> convert(List<MovieCatalogDO> movieCatalogDO);
}
