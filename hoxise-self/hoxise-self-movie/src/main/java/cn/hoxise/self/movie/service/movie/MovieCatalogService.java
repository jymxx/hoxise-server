package cn.hoxise.self.movie.service.movie;

import cn.hoxise.common.base.pojo.PageResult;
import cn.hoxise.self.movie.controller.movie.dto.MovieSimpleQueryDTO;
import cn.hoxise.self.movie.controller.movie.vo.MovieSimpleVO;
import cn.hoxise.self.movie.controller.movie.vo.MovieStatVO;
import cn.hoxise.self.movie.dal.entity.MovieCatalogDO;
import cn.hoxise.self.movie.pojo.constants.MovieRedisConstants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cache.annotation.Cacheable;

import java.util.Collection;
import java.util.List;

/**
* @author Hoxise
* @description 针对表【movie_catalog】的数据库操作Service
* @createDate 2025-12-22 07:34:01
*/
public interface MovieCatalogService extends IService<MovieCatalogDO> {

    Page<MovieCatalogDO> page(MovieSimpleQueryDTO queryDTO);

    PageResult<MovieSimpleVO> listPageContainDB(MovieSimpleQueryDTO queryDTO);

    /**
     * @Author: hoxise
     * @Description: 影视库数据 带缓存
     * @Date: 2025/12/23 上午1:10
     */
    PageResult<MovieSimpleVO> libraryDBCache(MovieSimpleQueryDTO queryDTO);

    @Cacheable(value = MovieRedisConstants.MOVIE_STAT_KEY)
    MovieStatVO statCount();

    List<MovieSimpleVO> randomQuery(Integer limit);

    /**
     * @Author: hoxise
     * @Description: 最新更新
     * @Date: 2025/12/22 下午4:44
     */
    List<MovieSimpleVO> LastUpdate();

    List<MovieCatalogDO> listNotIn(Collection<Long> notInIds);
}
