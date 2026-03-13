package cn.hoxise.module.movie.service.bangumi;

import cn.hoxise.module.movie.dal.entity.BangumiDbActorDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * MovieDbBangumiActorService
 *
 * @author Hoxise
 * @since 2026/01/14 15:05:44
 */
public interface BangumiDbActorService extends IService<BangumiDbActorDO> {

    /**
     * getByActorId
     *
     * @param actorId 演员id
     * @return Bangumi演员/声优表
     * @author hoxise
     * @since 2026/01/14 15:05:49
     */
    BangumiDbActorDO getByActorId(Long actorId);

    /**
     * 根据演员ids列出数据
     *
     * @param actorIds 演员ids
     * @return Bangumi演员数据
     * @author hoxise
     * @since 2026/01/14 15:06:06
     */
    List<BangumiDbActorDO> listByActorIds(Collection<Long> actorIds);
}
