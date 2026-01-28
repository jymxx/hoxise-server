package cn.hoxise.module.movie.service.tmdb;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * MovieDbService
 *
 * @author Hoxise
 * @since 2026/01/14 15:01:44
 */
public interface MovieDbService extends IService<MovieDbDO> {
    /**
     * 获取所有已经匹配的catalogids
     *
     * @return
     * @author hoxise
     * @since 2026/01/14 15:01:49
     */
    List<Long> getCatalogIdList();

    List<MovieDbDO> listByCatalogId(Collection<Long> catalogIds);

}
