package cn.hoxise.self.biz.service.movie.bangumi;

import cn.hoxise.self.biz.controller.movie.vo.MovieCharactersVO;
import cn.hoxise.self.biz.dal.entity.MovieDbBangumiCharacterDO;
import cn.hoxise.self.biz.pojo.dto.BangumiCharacterResponse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Hoxise
* @description 针对表【movie_db_bangumi_character(电影数据库角色表)】的数据库操作Service
* @createDate 2025-12-23 16:43:28
*/
public interface MovieDbBangumiCharacterService extends IService<MovieDbBangumiCharacterDO> {

    MovieDbBangumiCharacterDO getByCharacterId(Long characterId);

    /**
     * @description: 获取角色信息
     * @param	catalogId 目录id
     * @author: hoxise
     * @date: 2025/12/23 下午7:28
     */
    List<MovieCharactersVO> getCharacters(Long catalogId);

    void removeByCatalogId(Long catalogId);
}
