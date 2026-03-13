package cn.hoxise.module.movie.api;


import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.movie.api.dto.BangumiDbCharacterRespDTO;
import cn.hoxise.module.movie.dal.entity.BangumiDbCharacterDO;
import cn.hoxise.module.movie.service.MovieCatalogService;
import cn.hoxise.module.movie.service.bangumi.BangumiDbCharacterService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * BangumiDbCharacterApiImpl
 *
 * @author hoxise
 * @since 2026/01/28 12:39:33
 */
@RestController
public class BangumiDbCharacterApiImpl implements BangumiDbCharacterApi {

    @Resource
    private BangumiDbCharacterService bangumiDbCharacterService;

    @Resource
    private MovieCatalogService movieCatalogService;

    @Override
    public CommonResult<List<BangumiDbCharacterRespDTO>> list(Collection<Long> catalogIds) {
        List<Long> bangumiIds = movieCatalogService.getBangumiIdByCatalogId(catalogIds).stream().distinct().toList();
        List<BangumiDbCharacterDO> list = bangumiDbCharacterService.list(Wrappers.lambdaQuery(BangumiDbCharacterDO.class)
                .in(!catalogIds.isEmpty(), BangumiDbCharacterDO::getBangumiId, bangumiIds));
        return CommonResult.success(BeanUtil.copyToList(list, BangumiDbCharacterRespDTO.class));
    }
}
