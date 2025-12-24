package cn.hoxise.self.biz.service.movie;

import cn.hoxise.self.biz.controller.movie.vo.MovieCharactersVO;
import cn.hoxise.self.biz.dal.entity.MovieDbBangumiCharacterDO;
import cn.hoxise.self.biz.dal.entity.MovieDbBangumiDO;
import cn.hoxise.self.biz.dal.entity.MovieDbBangumiInfoboxDO;
import cn.hoxise.self.biz.pojo.constants.MovieRedisConstants;
import cn.hoxise.self.biz.service.tmdb.TMDBMulitSearchResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author hoxise
 * @Description: 影视管理
 * @Date 2025-12-22 下午3:47
 */
public interface MovieManageService {

    @Transactional
    void dirScan(boolean scanUpdate);

    void allMatchingBangumi(boolean isAllUpdate);

    @Transactional
    void saveBangumiWithInfobox(MovieDbBangumiDO bangumi, List<MovieDbBangumiInfoboxDO> infoboxList);

    void matchCharacters();

    @Transactional
    void apiUpdateCharacters(Long bangumiId,Long catalogid);

    void matchEpisode();

    @Transactional
    void apiUpdateEpisode(Long bangumiId, Long catalogid);

    void allMatchingTMDB();

    @Cacheable(cacheNames = MovieRedisConstants.TMDB_SEARCHMULTI_KEY, key = "#keyword")
    TMDBMulitSearchResponse searchMultiCache(String keyword);
}
