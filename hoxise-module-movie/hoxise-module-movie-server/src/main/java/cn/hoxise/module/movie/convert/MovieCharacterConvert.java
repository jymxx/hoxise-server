package cn.hoxise.module.movie.convert;

import cn.hoxise.module.movie.controller.movie.vo.MovieCharactersVO;
import cn.hoxise.module.movie.dal.entity.BangumiDbCharacterDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * MovieCharacterConvert
 *
 * @author hoxise
 * @since 2026/01/14 14:56:20
 */
@Mapper
public interface MovieCharacterConvert
{
    MovieCharacterConvert INSTANCE = Mappers.getMapper(MovieCharacterConvert.class);

    @Mapping(target = "actors", ignore = true)//忽略对象的映射
    MovieCharactersVO convert(BangumiDbCharacterDO characterDO);

    @Mapping(target = "actors", ignore = true)
    List<MovieCharactersVO> convert(List<BangumiDbCharacterDO> characterDO);
}
