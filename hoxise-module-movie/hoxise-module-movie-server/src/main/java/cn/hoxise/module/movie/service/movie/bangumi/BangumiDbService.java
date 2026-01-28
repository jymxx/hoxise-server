package cn.hoxise.module.movie.service.movie.bangumi;

import cn.hoxise.module.movie.controller.movie.vo.MovieDetailVO;
import cn.hoxise.module.movie.dal.entity.BangumiDbDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * MovieDbBangumiService
 *
 * @author Hoxise
 * @since 2026/01/14 15:17:20
 */
public interface BangumiDbService extends IService<BangumiDbDO> {

    /**
     * 获取所有目录id集合 不包括被逻辑删除的
     *
     * @return 所有目录ids
     * @author hoxise
     * @since 2026/01/14 15:17:21
     */
    List<Long> getCatalogIdList();

    /**
     * 根据目录id获取数据
     *
     * @param catalogIds 目录id
     * @return 影视db数据
     * @author hoxise
     * @since 2026/01/14 15:17:45
     */
    List<BangumiDbDO> listByCatalogId(Collection<Long> catalogIds);

    /**
     * 根据目录id获取单个数据
     *
     * @param catalogId 目录id
     * @return 影视数据
     * @author hoxise
     * @since 2026/01/14 15:18:13
     */
    BangumiDbDO getByCatalogId(Long catalogId);

    /**
     * 获取db详情
     *
     * @param catalogId 目录id
     * @return 影视详情
     * @author hoxise
     * @since 2026/01/14 15:18:54
     */
    MovieDetailVO detailByCatalogId(Long catalogId);


}
