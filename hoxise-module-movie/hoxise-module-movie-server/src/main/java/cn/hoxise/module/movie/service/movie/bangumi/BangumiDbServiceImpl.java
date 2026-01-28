package cn.hoxise.module.movie.service.movie.bangumi;

import cn.hoxise.module.movie.controller.movie.vo.MovieDetailVO;
import cn.hoxise.module.movie.convert.MovieDbBangumiConvert;
import cn.hoxise.module.movie.dal.entity.BangumiDbDO;
import cn.hoxise.module.movie.dal.entity.BangumiDbInfoboxDO;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hoxise.module.movie.dal.mapper.MovieDbBangumiMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 影视db bangumi服务实现类
 *
 * @author Hoxise
 * @since 2026/01/14 15:19:11
 */
@Service
public class BangumiDbServiceImpl extends ServiceImpl<MovieDbBangumiMapper, BangumiDbDO>
    implements BangumiDbService {

    @Resource
    private BangumiDbInfoboxService bangumiDbInfoboxService;

    @Override
    public List<Long> getCatalogIdList(){
        return  this.listMaps(Wrappers.lambdaQuery(BangumiDbDO.class)
                        .select(BangumiDbDO::getCatalogid))
                .stream()
                .map(map -> (Long) map.get("catalogid"))
                .distinct()
                .toList();
    }
    @Override
    public List<BangumiDbDO> listByCatalogId(Collection<Long> catalogIds) {
            return this.list(Wrappers.lambdaQuery(BangumiDbDO.class)
                    .in(BangumiDbDO::getCatalogid, catalogIds));
    }

    @Override
    public BangumiDbDO getByCatalogId(Long catalogId) {
        List<BangumiDbDO> list = this.list(Wrappers.lambdaQuery(BangumiDbDO.class).eq(BangumiDbDO::getCatalogid, catalogId));
        if (!list.isEmpty()){
            return list.getFirst();
        }
        return null;
    }

    @Override
    public MovieDetailVO detailByCatalogId(Long catalogId) {
        BangumiDbDO movieDb = getByCatalogId(catalogId);
        Assert.notNull(movieDb, "未找到该数据:"+catalogId);
        List<BangumiDbInfoboxDO> service = bangumiDbInfoboxService.getByCatalogId(catalogId);

        MovieDetailVO convert = MovieDbBangumiConvert.INSTANCE.convert(movieDb);
        convert.setInfobox(service);
        return convert;
    }



}




