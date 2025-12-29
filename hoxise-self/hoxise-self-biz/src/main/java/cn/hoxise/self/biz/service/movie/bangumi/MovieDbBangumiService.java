package cn.hoxise.self.biz.service.movie.bangumi;

import cn.hoxise.self.biz.controller.movie.vo.MovieDetailVO;
import cn.hoxise.self.biz.dal.entity.MovieCatalogDO;
import cn.hoxise.self.biz.dal.entity.MovieDbBangumiDO;
import cn.hoxise.self.biz.pojo.dto.BangumiSearchSubjectResponse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
* @author Hoxise
* @description 针对表【movie_db_bangumi(影视数据 匹配Bangumi API)】的数据库操作Service
* @createDate 2025-12-23 10:47:43
*/
public interface MovieDbBangumiService extends IService<MovieDbBangumiDO> {

    List<Long> getCatalogIdList();

    List<MovieDbBangumiDO> listByCatalogId(Collection<Long> catalogIds);

    MovieDbBangumiDO getByCatalogId(Long catalogId);

    MovieDetailVO detailByCatalogId(Long catalogId);


}
