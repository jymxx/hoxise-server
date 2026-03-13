package cn.hoxise.module.movie.service.bangumi;

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
    public BangumiDbDO getByBangumiId(Long bangumiId) {
        return getOne(Wrappers.lambdaQuery(BangumiDbDO.class).eq(BangumiDbDO::getBangumiId, bangumiId));
    }

    @Override
    public List<BangumiDbDO> listByBangumiIds(Collection<Long> bangumiIds) {
        return list(Wrappers.lambdaQuery(BangumiDbDO.class)
                .in(!bangumiIds.isEmpty(), BangumiDbDO::getBangumiId, bangumiIds));
    }

    @Override
    public MovieDetailVO detailByBangumiId(Long bangumiId) {
        Assert.notNull(bangumiId, "参数不能为空");
        BangumiDbDO movieDb = getByBangumiId(bangumiId);
        Assert.notNull(movieDb, "未找到该数据的详细信息");
        //信息框数据
        List<BangumiDbInfoboxDO> service = bangumiDbInfoboxService.getByBangumiId(bangumiId);
        MovieDetailVO convert = MovieDbBangumiConvert.INSTANCE.convert(movieDb);
        convert.setInfobox(service);
        return convert;
    }
}




