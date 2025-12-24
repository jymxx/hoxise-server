package cn.hoxise.self.biz.service.movie;

import cn.hoxise.self.biz.dal.entity.MovieDbBangumiActorDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Hoxise
* @description 针对表【movie_db_bangumi_actor(Bangumi演员/声优表)】的数据库操作Service
* @createDate 2025-12-23 16:43:28
*/
public interface MovieDbBangumiActorService extends IService<MovieDbBangumiActorDO> {

    MovieDbBangumiActorDO getByActorId(Long actorId);
}
