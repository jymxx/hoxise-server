package cn.hoxise.self.movie.service.tmdb;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
* @author Hoxise
* @description 针对表【movie_db】的数据库操作Service
* @createDate 2025-12-22 07:34:01
*/
public interface MovieDbService extends IService<MovieDbDO> {
    /**
     * @Author: hoxise
     * @Description: 获取所有已经匹配的catalogids
     * @Date: 2025/12/22 下午1:03
     */
    List<Long> getCatalogIdList();

    List<MovieDbDO> listByCatalogId(Collection<Long> catalogIds);

}
