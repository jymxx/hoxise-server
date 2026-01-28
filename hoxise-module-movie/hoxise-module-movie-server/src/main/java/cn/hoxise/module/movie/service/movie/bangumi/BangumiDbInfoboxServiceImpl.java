package cn.hoxise.module.movie.service.movie.bangumi;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hoxise.module.movie.dal.entity.BangumiDbInfoboxDO;
import cn.hoxise.module.movie.dal.mapper.MovieDbBangumiInfoboxMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 信息框服务实现类
 *
 * @author Hoxise
 * @since 2026/01/14 15:17:10
 */
@Service
public class BangumiDbInfoboxServiceImpl extends ServiceImpl<MovieDbBangumiInfoboxMapper, BangumiDbInfoboxDO>
    implements BangumiDbInfoboxService {


    @Override
    public void removeByCatalogId(Long catalogid) {
        this.remove(Wrappers.<BangumiDbInfoboxDO>lambdaQuery().eq(BangumiDbInfoboxDO::getCatalogid, catalogid));
    }

    @Override
    public List<BangumiDbInfoboxDO> getByCatalogId(Long catalogid) {
        return this.list(Wrappers.<BangumiDbInfoboxDO>lambdaQuery().eq(BangumiDbInfoboxDO::getCatalogid, catalogid));
    }




}




