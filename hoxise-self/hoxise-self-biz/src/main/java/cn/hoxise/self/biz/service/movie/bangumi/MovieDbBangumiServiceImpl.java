package cn.hoxise.self.biz.service.movie.bangumi;

import cn.hoxise.common.base.exception.ServiceException;
import cn.hoxise.common.base.utils.date.DateUtil;
import cn.hoxise.self.biz.controller.movie.vo.MovieDetailVO;
import cn.hoxise.self.biz.convert.MovieDbBangumiConvert;
import cn.hoxise.self.biz.dal.entity.MovieCatalogDO;
import cn.hoxise.self.biz.dal.entity.MovieDbBangumiInfoboxDO;
import cn.hoxise.self.biz.pojo.dto.BangumiSearchSubjectReq;
import cn.hoxise.self.biz.pojo.dto.BangumiSearchSubjectResponse;
import cn.hoxise.self.biz.pojo.enums.bangumi.BangumiSubjectTypeEnum;
import cn.hoxise.self.biz.utils.BangumiUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hoxise.self.biz.dal.entity.MovieDbBangumiDO;
import cn.hoxise.self.biz.dal.mapper.MovieDbBangumiMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
* @author Hoxise
* @description 针对表【movie_db_bangumi(影视数据 匹配Bangumi API)】的数据库操作Service实现
* @createDate 2025-12-23 10:47:43
*/
@Service
public class MovieDbBangumiServiceImpl extends ServiceImpl<MovieDbBangumiMapper, MovieDbBangumiDO>
    implements MovieDbBangumiService{

    @Resource
    private MovieDbBangumiInfoboxService movieDbBangumiInfoboxService;

    @Override
    public List<Long> getCatalogIdList(){
        return  this.listMaps(Wrappers.lambdaQuery(MovieDbBangumiDO.class)
                        .select(MovieDbBangumiDO::getCatalogid))
                .stream()
                .map(map -> (Long) map.get("catalogid"))
                .distinct()
                .toList();
    }
    @Override
    public List<MovieDbBangumiDO> listByCatalogId(Collection<Long> catalogIds) {
            return this.list(Wrappers.lambdaQuery(MovieDbBangumiDO.class)
                    .in(MovieDbBangumiDO::getCatalogid, catalogIds));
    }

    @Override
    public MovieDbBangumiDO getByCatalogId(Long catalogId) {
        List<MovieDbBangumiDO> list = this.list(Wrappers.lambdaQuery(MovieDbBangumiDO.class).eq(MovieDbBangumiDO::getCatalogid, catalogId));
        if (!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    @Override
    public MovieDetailVO detailByCatalogId(Long catalogId) {
        MovieDbBangumiDO movieDb = getByCatalogId(catalogId);
        Assert.notNull(movieDb, "未找到该数据");
        List<MovieDbBangumiInfoboxDO> service = movieDbBangumiInfoboxService.getByCatalogId(catalogId);

        MovieDetailVO convert = MovieDbBangumiConvert.INSTANCE.convert(movieDb);
        convert.setInfobox(service);
        return convert;
    }



}




