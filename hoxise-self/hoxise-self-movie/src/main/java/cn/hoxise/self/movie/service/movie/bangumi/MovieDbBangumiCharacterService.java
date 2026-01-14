package cn.hoxise.self.movie.service.movie.bangumi;

import cn.hoxise.self.movie.controller.movie.vo.MovieCharactersVO;
import cn.hoxise.self.movie.dal.entity.MovieDbBangumiCharacterDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * MovieDbBangumiCharacterService
 *
 * @author Hoxise
 * @since 2026/01/14 15:13:29
 */
public interface MovieDbBangumiCharacterService extends IService<MovieDbBangumiCharacterDO> {

    /**
     * getByCharacterId
     *
     * @param characterId 角色id
     * @return 电影数据库角色表
     * @author hoxise
     * @since 2026/01/14 15:13:31
     */
    MovieDbBangumiCharacterDO getByCharacterId(Long characterId);

    /**
     * 根据catalog获取角色列表
     *
     * @param catalogId 目录id
     * @return 角色列表
     * @author hoxise
     * @since 2026/01/14 15:13:41
     */
    List<MovieCharactersVO> getCharacters(Long catalogId);

    /**
     * 删除角色
     *
     * @param catalogId 目录id
     * @author hoxise
     * @since 2026/01/14 15:13:56
     */
    void removeByCatalogId(Long catalogId);
}
