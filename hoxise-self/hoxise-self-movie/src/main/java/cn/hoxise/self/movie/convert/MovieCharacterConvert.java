package cn.hoxise.self.movie.convert;

import cn.hoxise.self.movie.controller.movie.vo.MovieCharactersVO;
import cn.hoxise.self.movie.dal.entity.MovieDbBangumiCharacterDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author hoxise
 * @Description: MovieCharacterConvert
 * @Date 2025-12-23 下午6:35
 */
@Mapper
public interface MovieCharacterConvert
{
    MovieCharacterConvert INSTANCE = Mappers.getMapper(MovieCharacterConvert.class);

    @Mapping(target = "actors", ignore = true)//忽略对象的映射
    MovieCharactersVO convert(MovieDbBangumiCharacterDO characterDO);

    @Mapping(target = "actors", ignore = true)
    List<MovieCharactersVO> convert(List<MovieDbBangumiCharacterDO> characterDO);
}
