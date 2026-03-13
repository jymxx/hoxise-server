package cn.hoxise.module.movie.service.bangumi;

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
     * 通过bangumiId获取db信息
     *
     * @param bangumiId bangumiId
     * @return db信息
     * @author hoxise
     * @since 2026/01/14 15:18:07
     */
    BangumiDbDO getByBangumiId(Long bangumiId);

    /**
     * 通过bangumiIds批量获取db信息
     *
     * @param bangumiIds bangumiIds
     * @return db信息
     * @author hoxise
     * @since 2026/01/14 15:18:07
     */
    List<BangumiDbDO> listByBangumiIds(Collection<Long> bangumiIds);

    /**
     * 获取db详情
     *
     * @param bangumiId bangumiId
     * @return 影视详情
     * @author hoxise
     * @since 2026/01/14 15:18:54
     */
    MovieDetailVO detailByBangumiId(Long bangumiId);

}
