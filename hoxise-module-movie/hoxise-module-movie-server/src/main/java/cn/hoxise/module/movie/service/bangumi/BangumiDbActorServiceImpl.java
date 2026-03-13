package cn.hoxise.module.movie.service.bangumi;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hoxise.module.movie.dal.entity.BangumiDbActorDO;
import cn.hoxise.module.movie.dal.mapper.MovieDbBangumiActorMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 演员声优操作实现
 *
 * @author Hoxise
 * @since 2026/01/14 15:05:22
 */
@Service
public class BangumiDbActorServiceImpl extends ServiceImpl<MovieDbBangumiActorMapper, BangumiDbActorDO>
    implements BangumiDbActorService {


    @Override
    public BangumiDbActorDO getByActorId(Long actorId) {
        return this.getOne(Wrappers.lambdaQuery(BangumiDbActorDO.class).eq(BangumiDbActorDO::getActorId, actorId));
    }

    @Override
    public List<BangumiDbActorDO> listByActorIds(Collection<Long> actorIds) {
        return this.list(Wrappers.lambdaQuery(BangumiDbActorDO.class).in(BangumiDbActorDO::getActorId, actorIds));
    }


}




