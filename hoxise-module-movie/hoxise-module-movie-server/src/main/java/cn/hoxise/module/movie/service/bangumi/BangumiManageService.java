package cn.hoxise.module.movie.service.bangumi;

import cn.hoxise.module.movie.dal.entity.BangumiDbDO;
import cn.hoxise.module.movie.dal.entity.MovieCatalogDO;
import cn.hoxise.module.movie.pojo.dto.BangumiSearchSubjectResponse;

import java.util.List;

/**
 * Bangumi影视管理
 *
 * @author hoxise
 * @since 2026/01/14 15:10:44
 */
public interface BangumiManageService {


    /**
     * 根据名称从Bangumi查询数据
     *
     * @param name 名称
     * @return 条目列表
     * @author hoxise
     * @since 2026/01/14 15:10:50
     */
    List<BangumiSearchSubjectResponse.Subject> queryByNameFromBangumi(String name);

    /**
     * checkExistOrUpdate
     *
     * @param bangumiId   条目id
     * @param forceUpdate 是否强制更新 通常可以force
     * @return 条目信息
     * @author hoxise
     * @since 2026/01/14 15:11:09
     */
    BangumiDbDO checkExistOrUpdate(Long bangumiId, boolean forceUpdate);

    /**
     * 保存Bangumi数据
     *
     * @param bangumiId 条目id
     * @param forceUpdate 是否强制更新
     * @author hoxise
     * @since 2026/01/14 15:11:45
     */
    void apiUpdateDbWithInfobox(Long bangumiId, boolean forceUpdate);

    /**
     * Api获取角色和CV信息并把没有的数据保存进数据库
     *
     * @param bangumiId 条目id
     * @param forceUpdate 是否强制更新
     * @author hoxise
     * @since 2026/01/14 15:12:01
     */
    void apiUpdateCharactersAndActors(Long bangumiId, boolean forceUpdate);

    /**
     * Api获取章节信息并把没有的数据保存进数据库
     *
     * @param bangumiId 条目id
     * @param forceUpdate 是否强制更新
     * @author hoxise
     * @since 2026/01/14 15:12:21
     */
    void apiUpdateEpisode(Long bangumiId, boolean forceUpdate);

}
