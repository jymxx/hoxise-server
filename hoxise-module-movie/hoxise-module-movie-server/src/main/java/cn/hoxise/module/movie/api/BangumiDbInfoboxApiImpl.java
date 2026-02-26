package cn.hoxise.module.movie.api;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.movie.api.dto.BangumiDbInfoboxDTO;
import cn.hoxise.module.movie.dal.entity.BangumiDbInfoboxDO;
import cn.hoxise.module.movie.service.movie.bangumi.BangumiDbInfoboxService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * 信息框api实现
 *
 * @author hoxise
 * @since 2026/1/28 上午10:36
 */
@RestController
public class BangumiDbInfoboxApiImpl implements BangumiDbInfoboxApi {

    @Resource
    private BangumiDbInfoboxService bangumiDbInfoboxService;

    @Override
    public CommonResult<List<BangumiDbInfoboxDTO>> list(Collection<Long> catalogids) {
        List<BangumiDbInfoboxDO> list = bangumiDbInfoboxService.list(Wrappers.lambdaQuery(BangumiDbInfoboxDO.class)
                .in(catalogids != null && !catalogids.isEmpty(), BangumiDbInfoboxDO::getCatalogid, catalogids));
        return CommonResult.success(BeanUtil.copyToList(list, BangumiDbInfoboxDTO.class));
    }
}
