package cn.hoxise.self.biz.service.movie.bangumi;

import cn.hoxise.self.biz.dal.entity.MovieDbBangumiInfoboxDO;
import cn.hoxise.self.biz.pojo.dto.BangumiSearchSubjectResponse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Hoxise
* @description 针对表【movie_db_bangumi_infobox(Bangumi 的infobox)】的数据库操作Service
* @createDate 2025-12-23 10:47:43
*/
public interface MovieDbBangumiInfoboxService extends IService<MovieDbBangumiInfoboxDO> {

    void removeByCatalogId(Long catalogId);

    List<MovieDbBangumiInfoboxDO> getByCatalogId(Long catalogid);

}
