package cn.hoxise.self.biz.convert;

import cn.hoxise.self.biz.controller.movie.vo.MovieDetailVO;
import cn.hoxise.self.biz.dal.entity.MovieDbBangumiDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author hoxise
 * @Description:
 * @Date 2025-12-23 下午3:24
 */
@Mapper
public interface MovieDbBangumiConvert {

    MovieDbBangumiConvert INSTANCE = Mappers.getMapper(MovieDbBangumiConvert.class);

    MovieDetailVO convert(MovieDbBangumiDO movieDbBangumiDO);

    List<MovieDetailVO> convert(List<MovieDbBangumiDO> movieDbBangumiDO);
}
