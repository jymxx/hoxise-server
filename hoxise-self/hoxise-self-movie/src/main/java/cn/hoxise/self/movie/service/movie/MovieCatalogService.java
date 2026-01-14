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
 * MovieCatalogService
 *
 * @author Hoxise
 * @since 2026/01/14 15:19:26
 */
public interface MovieCatalogService extends IService<MovieCatalogDO> {

    /**
     * 分页查询
     *
     * @param queryDTO 查询参数
     * @return 分页结果
     * @author hoxise
     * @since 2026/01/14 15:19:29
     */
    Page<MovieCatalogDO> page(MovieSimpleQueryDTO queryDTO);

    /**
     * 分页查询VO结果 含匹配的DB数据
     *
     * @param queryDTO 查询参数
     * @return 分页结果
     * @author hoxise
     * @since 2026/01/14 15:19:43
     */
    PageResult<MovieSimpleVO> listPageContainDb(MovieSimpleQueryDTO queryDTO);

    /**
     * 影视库数据 带缓存
     *
     * @param queryDTO 查询结果
     * @return 分页结果
     * @author hoxise
     * @since 2026/01/14 15:20:45
     */
    PageResult<MovieSimpleVO> libraryDbCache(MovieSimpleQueryDTO queryDTO);

    /**
     * 统计数据
     *
     * @return 影视统计数据
     * @author hoxise
     * @since 2026/01/14 15:21:04
     */
    MovieStatVO statCount();

    /**
     * 随机查询
     * *目前用的mysql函数以保证随机性,访问量大需要改
     *
     * @param limit 限制数量
     * @return 随机数据
     * @author hoxise
     * @since 2026/01/14 15:21:16
     */
    List<MovieSimpleVO> randomQuery(Integer limit);

    /**
     * 最后更新的数据
     *
     * @return 数据集
     * @author hoxise
     * @since 2026/01/14 15:22:05
     */
    List<MovieSimpleVO> lastUpdate();

    List<MovieCatalogDO> listNotIn(Collection<Long> notInIds);
}
