package cn.hoxise.self.biz.service.movie;

import cn.hoxise.self.biz.dal.entity.MovieDbBangumiInfoboxDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Hoxise
* @description 针对表【movie_db_bangumi_infobox(Bangumi 的infobox)】的数据库操作Service
* @createDate 2025-12-23 10:47:43
*/
public interface MovieDbBangumiInfoboxService extends IService<MovieDbBangumiInfoboxDO> {

    void removeByBangumiId(Long bangumiId);

    List<MovieDbBangumiInfoboxDO> getByBangumiId(Long bangumiId);
}
