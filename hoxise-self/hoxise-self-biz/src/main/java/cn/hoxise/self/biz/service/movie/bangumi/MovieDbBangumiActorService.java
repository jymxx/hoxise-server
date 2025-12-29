package cn.hoxise.self.biz.service.movie.bangumi;

import cn.hoxise.self.biz.dal.entity.MovieDbBangumiActorDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
* @author Hoxise
* @description 针对表【movie_db_bangumi_actor(Bangumi演员/声优表)】的数据库操作Service
* @createDate 2025-12-23 16:43:28
*/
public interface MovieDbBangumiActorService extends IService<MovieDbBangumiActorDO> {

    MovieDbBangumiActorDO getByActorId(Long actorId);

    List<MovieDbBangumiActorDO> listByActorIds(Collection<Long> actorIds);
}
