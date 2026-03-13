package cn.hoxise.module.movie.api;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.movie.api.dto.BangumiDbInfoboxRespDTO;
import cn.hoxise.module.movie.dal.entity.BangumiDbInfoboxDO;
import cn.hoxise.module.movie.service.MovieCatalogService;
import cn.hoxise.module.movie.service.bangumi.BangumiDbInfoboxService;
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

    @Resource
    private MovieCatalogService movieCatalogService;

    @Override
    public CommonResult<List<BangumiDbInfoboxRespDTO>> list(Collection<Long> catalogIds) {
        List<Long> bangumiIds = movieCatalogService.getBangumiIdByCatalogId(catalogIds).stream().distinct().toList();
        List<BangumiDbInfoboxDO> list = bangumiDbInfoboxService.list(Wrappers.lambdaQuery(BangumiDbInfoboxDO.class)
                .in(!bangumiIds.isEmpty(), BangumiDbInfoboxDO::getBangumiId, bangumiIds));
        return CommonResult.success(BeanUtil.copyToList(list, BangumiDbInfoboxRespDTO.class));
    }
}
