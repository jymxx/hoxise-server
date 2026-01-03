package cn.hoxise.self.movie.service.movie.bangumi;

import cn.hoxise.self.movie.dal.entity.MovieCatalogDO;
import cn.hoxise.self.movie.pojo.constants.MovieRedisConstants;
import cn.hoxise.self.movie.pojo.dto.BangumiSearchSubjectResponse;
import cn.hoxise.self.movie.service.tmdb.TMDBMulitSearchResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author hoxise
 * @Description: Bangumi影视管理
 * @Date 2025-12-22 下午3:47
 */
public interface MovieBangumiManageService {

    /**
     * 根据名称从Bangumi查询数据
     * @param name 名称
     * @return
     */
    List<BangumiSearchSubjectResponse.Subject> queryByNameFromBangumi(String name);

    /**
     * @description: 更新指定id的Bangumi信息
     * @param	catalogid 目录id
     * @param	bangumiId 条目id
     * @author: hoxise
     * @date: 2025/12/23 下午6:50
     */
    @Transactional
    void updateBangumi(Long catalogid, Long bangumiId);

    /**
     * @description: 扫描目录
     * @param	scanUpdate 是否更新已存在的条目
     * @author: hoxise
     * @date: 2025/12/23 下午6:50
     */
    @Transactional
    void dirScan(boolean scanUpdate);

    /**
     * @Author: hoxise
     * @Params: [isAllUpdate] 是否全量更新 false则只新增
     * @Description: 匹配Bangumi数据
     * @Date: 2025/12/22 下午4:34
     */
    void allMatchingBangumi(boolean isAllUpdate);

    /**
     * @description: 保存Bangumi数据
     * @param	catalog 目录
     * @param	subject 搜索结果
     * @author: hoxise
     * @date: 2025/12/23 下午6:50
     */
    @Transactional
    void saveBangumiWithInfobox(MovieCatalogDO catalog, BangumiSearchSubjectResponse.Subject subject);

    /**
     * @description: 匹配角色 和 CV
     * @author: hoxise
     * @date: 2025/12/23 下午6:50
     */
    void matchCharacters();

    /**
     * @description: Api获取角色和CV信息并把没有的数据保存进数据库
     * @param	bangumiId 条目id
     * @author: hoxise
     * @date: 2025/12/23 下午6:50
     */
    @Transactional
    void apiUpdateCharactersAndActors(Long bangumiId,Long catalogid);

    /**
     * @description: 匹配章节信息
     * @author: hoxise
     * @date: 2025/12/23 下午6:50
     */
    void matchEpisode();

    /**
     * @description: Api获取章节信息并把没有的数据保存进数据库
     * @param	bangumiId 条目id
     * @param	catalogid 目录id
     * @author: hoxise
     * @date: 2025/12/23 下午6:50
     */
    @Transactional
    void apiUpdateEpisode(Long bangumiId, Long catalogid);

    void allMatchingTMDB();

    @Cacheable(cacheNames = MovieRedisConstants.TMDB_SEARCHMULTI_KEY, key = "#keyword")
    TMDBMulitSearchResponse searchMultiCache(String keyword);
}
