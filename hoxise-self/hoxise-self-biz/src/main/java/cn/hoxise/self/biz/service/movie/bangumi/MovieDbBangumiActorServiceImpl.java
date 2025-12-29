package cn.hoxise.self.biz.service.movie.bangumi;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hoxise.self.biz.dal.entity.MovieDbBangumiActorDO;
import cn.hoxise.self.biz.dal.mapper.MovieDbBangumiActorMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
* @author Hoxise
* @description 针对表【movie_db_bangumi_actor(Bangumi演员/声优表)】的数据库操作Service实现
* @createDate 2025-12-23 16:43:28
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




