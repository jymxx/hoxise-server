package cn.hoxise.module.movie.service.bangumi;

import cn.hoxise.module.movie.controller.movie.vo.MovieCharactersVO;
import cn.hoxise.module.movie.dal.entity.BangumiDbCharacterDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * MovieDbBangumiCharacterService
 *
 * @author Hoxise
 * @since 2026/01/14 15:13:29
 */
public interface BangumiDbCharacterService extends IService<BangumiDbCharacterDO> {

    /**
     * getByCharacterId
     *
     * @param characterId 角色id
     * @return 电影数据库角色表
     * @author hoxise
     * @since 2026/01/14 15:13:31
     */
    BangumiDbCharacterDO getByCharacterId(Long characterId);

    /**
     * 根据catalog获取角色列表
     *
     * @param bangumiId bangumiId
     * @return 角色列表
     * @author hoxise
     * @since 2026/01/14 15:13:41
     */
    List<MovieCharactersVO> getCharacters(Long bangumiId);


    /**
     * 根据bangumiId删除角色
     *
     * @param bangumiId bangumiId
     * @author hoxise
     * @since 2026/03/06 17:18:40
     */
    void removeByBangumiId(Long bangumiId);
}
