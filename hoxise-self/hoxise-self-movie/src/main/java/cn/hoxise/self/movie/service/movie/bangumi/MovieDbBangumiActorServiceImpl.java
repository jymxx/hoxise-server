package cn.hoxise.self.movie.service.movie.bangumi;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hoxise.self.movie.dal.entity.MovieDbBangumiActorDO;
import cn.hoxise.self.movie.dal.mapper.MovieDbBangumiActorMapper;
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
public class MovieDbBangumiActorServiceImpl extends ServiceImpl<MovieDbBangumiActorMapper, MovieDbBangumiActorDO>
    implements MovieDbBangumiActorService{


    @Override
    public MovieDbBangumiActorDO getByActorId(Long actorId) {
        return this.getOne(Wrappers.lambdaQuery(MovieDbBangumiActorDO.class).eq(MovieDbBangumiActorDO::getActorId, actorId));
    }

    @Override
    public List<MovieDbBangumiActorDO> listByActorIds(Collection<Long> actorIds) {
        return this.list(Wrappers.lambdaQuery(MovieDbBangumiActorDO.class).in(MovieDbBangumiActorDO::getActorId, actorIds));
    }


}




