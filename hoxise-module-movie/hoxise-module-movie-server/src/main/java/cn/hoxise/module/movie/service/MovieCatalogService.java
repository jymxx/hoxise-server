package cn.hoxise.module.movie.service;

import cn.hoxise.common.base.pojo.PageResult;
import cn.hoxise.module.movie.controller.movie.dto.MovieSimpleQueryDTO;
import cn.hoxise.module.movie.controller.movie.dto.MovieUpdateDbDTO;
import cn.hoxise.module.movie.controller.movie.vo.MovieSimpleVO;
import cn.hoxise.module.movie.controller.movie.vo.MovieStatVO;
import cn.hoxise.module.movie.dal.entity.MovieCatalogDO;
import cn.hoxise.module.movie.pojo.dto.BangumiSearchSubjectResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * @param userId 用户 ID
     * @return 影视统计数据
     * @author hoxise
     * @since 2026/01/14 15:21:04
     */
    MovieStatVO statCount(Long userId);

    /**
     * 随机查询
     * *目前用的mysql函数以保证随机性,访问量大需要改
     *
     * @param limit 限制数量
     * @return 随机数据
     * @author hoxise
     * @since 2026/01/14 15:21:16
     */
    List<MovieSimpleVO> randomQuery(Integer limit,Long userid);

    /**
     * 最后更新的数据
     *
     * @return 数据集
     * @author hoxise
     * @since 2026/01/14 15:22:05
     */
    List<MovieSimpleVO> lastUpdate(Long userid);

    /**
     * 更新指定id的Bangumi信息
     *
     * @param dto 参数
     * @author hoxise
     * @since 2026/01/14 15:11:05
     */
    void updateBangumi(MovieUpdateDbDTO dto);

    /**
     * removeAndCache 逻辑删除并清理缓存
     *
     * @param catalogId 目录id
     * @author hoxise
     * @since 2026/01/26 18:06:20
     */
    void removeAndCache(Long catalogId);

    /**
     * 根据目录Id获取对应BangumiIds
     *
     * @param catalogIds 目录Id
     * @return bangumiIds
     * @author hoxise
     * @since 2026/03/12 20:57:57
     */
    List<Long> getBangumiIdByCatalogId(Collection<Long> catalogIds);
}
