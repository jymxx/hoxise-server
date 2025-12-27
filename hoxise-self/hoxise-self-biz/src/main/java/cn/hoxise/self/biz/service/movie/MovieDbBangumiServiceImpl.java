package cn.hoxise.self.biz.service.movie;

import cn.hoxise.common.base.exception.ServiceException;
import cn.hoxise.self.biz.controller.movie.vo.MovieCharactersVO;
import cn.hoxise.self.biz.controller.movie.vo.MovieDetailVO;
import cn.hoxise.self.biz.convert.MovieDbBangumiConvert;
import cn.hoxise.self.biz.dal.entity.MovieDbBangumiInfoboxDO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hoxise.self.biz.dal.entity.MovieDbBangumiDO;
import cn.hoxise.self.biz.dal.mapper.MovieDbBangumiMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        if (movieDb == null){
            throw new ServiceException("未找到该数据");
        }
        List<MovieDbBangumiInfoboxDO> service = movieDbBangumiInfoboxService.getByBangumiId(movieDb.getBangumiId());

        MovieDetailVO convert = MovieDbBangumiConvert.INSTANCE.convert(movieDb);
        convert.setInfobox(service);
        return convert;
    }

}




