package cn.hoxise.module.movie.api;


import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.movie.api.dto.BangumiDbCharacterDTO;
import cn.hoxise.module.movie.dal.entity.BangumiDbCharacterDO;
import cn.hoxise.module.movie.service.movie.bangumi.BangumiDbCharacterService;
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

    @Override
    public CommonResult<List<BangumiDbCharacterDTO>> list(Collection<Long> catalogids) {
        List<BangumiDbCharacterDO> list = bangumiDbCharacterService.list(Wrappers.lambdaQuery(BangumiDbCharacterDO.class)
                .in(catalogids != null && !catalogids.isEmpty(), BangumiDbCharacterDO::getCatalogid, catalogids));
        return CommonResult.success(BeanUtil.copyToList(list, BangumiDbCharacterDTO.class));
    }
}
