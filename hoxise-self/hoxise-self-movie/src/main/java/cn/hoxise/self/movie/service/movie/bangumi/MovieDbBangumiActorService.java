package cn.hoxise.self.movie.service.movie.bangumi;

import cn.hoxise.self.movie.dal.entity.MovieDbBangumiActorDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * MovieDbBangumiActorService
 *
 * @author Hoxise
 * @since 2026/01/14 15:05:44
 */
public interface MovieDbBangumiActorService extends IService<MovieDbBangumiActorDO> {

    /**
     * getByActorId
     *
     * @param actorId 演员id
     * @return Bangumi演员/声优表
     * @author hoxise
     * @since 2026/01/14 15:05:49
     */
    MovieDbBangumiActorDO getByActorId(Long actorId);

    /**
     * 根据演员ids列出数据
     *
     * @param actorIds 演员ids
     * @return Bangumi演员数据
     * @author hoxise
     * @since 2026/01/14 15:06:06
     */
    List<MovieDbBangumiActorDO> listByActorIds(Collection<Long> actorIds);
}
