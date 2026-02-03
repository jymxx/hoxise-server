package cn.hoxise.module.movie.service.movie.bangumi;

import cn.hoxise.module.movie.dal.entity.MovieCatalogDO;
import cn.hoxise.module.movie.pojo.dto.BangumiSearchSubjectResponse;
import cn.hoxise.module.movie.service.tmdb.TMDBMulitSearchResponse;
import org.springframework.transaction.annotation.Transactional;

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
     * 更新指定id的Bangumi信息
     *
     * @param catalogid 目录id
     * @param bangumiId 条目id
     * @author hoxise
     * @since 2026/01/14 15:11:05
     */
    void updateBangumi(Long catalogid, Long bangumiId);

    /**
     * 扫描目录
     *
     * @param scanUpdate 是否更新已存在的条目
     * @author hoxise
     * @since 2026/01/14 15:11:12
     */
    void dirScan(boolean scanUpdate);

    /**
     * 匹配bangumi数据
     *
     * @param isAllUpdate [isAllUpdate] 是否全量更新 false则只新增
     * @author hoxise
     * @since 2026/01/14 15:11:29
     */
    void allMatchingBangumi(boolean isAllUpdate);

    /**
     * 保存Bangumi数据
     *
     * @param catalog 目录
     * @param subject 搜索结果
     * @author hoxise
     * @since 2026/01/14 15:11:45
     */
    void saveBangumiWithInfobox(MovieCatalogDO catalog, BangumiSearchSubjectResponse.Subject subject);

    /**
     * 匹配角色 和 CV
     *
     * @author hoxise
     * @since 2026/01/14 15:11:55
     */
    void matchCharacters();

    /**
     * Api获取角色和CV信息并把没有的数据保存进数据库
     *
     * @param bangumiId 条目id
     * @param catalogid 系统里的目录id
     * @author hoxise
     * @since 2026/01/14 15:12:01
     */
    void apiUpdateCharactersAndActors(Long bangumiId,Long catalogid);

    /**
     * 匹配章节信息
     *
     * @author hoxise
     * @since 2026/01/14 15:12:15
     */
    void matchEpisode();

    /**
     * Api获取章节信息并把没有的数据保存进数据库
     *
     * @param bangumiId 条目id
     * @param catalogid 目录id
     * @author hoxise
     * @since 2026/01/14 15:12:21
     */
    void apiUpdateEpisode(Long bangumiId, Long catalogid);

    /**
     * allMatchingTMDB
     *
     * @author hoxise
     * @since 2026/01/14 15:12:25
     */
    void allMatchingTMDB();

    /**
     * searchMultiCache
     *
     * @param keyword 关键字
     * @return TMDB多重查询结果
     * @author hoxise
     * @since 2026/01/14 15:12:31
     */
    TMDBMulitSearchResponse searchMultiCache(String keyword);
}
