package cn.hoxise.module.movie.service.movie.bangumi;

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
     * 根据catalogid删除
     *
     * @param catalogId 目录id
     * @author hoxise
     * @since 2026/01/14 15:15:58
     */
    void removeByCatalogId(Long catalogId);

    /**
     * 根据目录id获取所有关联的信息框
     *
     * @param catalogid 目录id
     * @return 信息框列表
     * @author hoxise
     * @since 2026/01/14 15:16:41
     */
    List<BangumiDbInfoboxDO> getByCatalogId(Long catalogid);

}
