package cn.hoxise.module.movie.service.bangumi;

import cn.hoxise.module.movie.dal.entity.BangumiDbInfoboxDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * MovieDbBangumiInfoboxService
 *
 * @author Hoxise
 * @since 2026/01/14 15:15:57
 */
public interface BangumiDbInfoboxService extends IService<BangumiDbInfoboxDO> {

    /**
     * 根据bangumiId删除
     *
     * @param bangumiId bangumi
     * @author hoxise
     * @since 2026/02/28 21:36:39
     */
    void removeByBangumiId(Long bangumiId);

    /**
     * 获取关联的信息框
     *
     * @param bangumiId bangumiId
     * @return 信息框列表
     * @author hoxise
     * @since 2026/01/14 15:16:41
     */
    List<BangumiDbInfoboxDO> getByBangumiId(Long bangumiId);
}
