package cn.hoxise.module.movie.api;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.movie.api.dto.BangumiDbActorRespDTO;
import cn.hoxise.module.movie.dal.entity.BangumiDbActorDO;
import cn.hoxise.module.movie.service.bangumi.BangumiDbActorService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * 演员数据
 *
 * @author hoxise
 * @since 2026/1/28 下午12:58
 */
@RestController
public class BangumiDbActorApiImpl implements BangumiDbActorApi {

    @Resource
    private BangumiDbActorService bangumiDbActorService;

    @Override
    public CommonResult<List<BangumiDbActorRespDTO>> list(Collection<Long> actorIds) {
        List<BangumiDbActorDO> list = bangumiDbActorService.list(Wrappers.lambdaQuery(BangumiDbActorDO.class)
                .in(actorIds != null && !actorIds.isEmpty(), BangumiDbActorDO::getActorId, actorIds));
        return CommonResult.success(BeanUtil.copyToList(list, BangumiDbActorRespDTO.class));
    }
}
