package cn.hoxise.module.movie.api;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.common.base.pojo.PageResult;
import cn.hoxise.module.movie.api.dto.BangumiDbDTO;
import cn.hoxise.module.movie.dal.entity.BangumiDbDO;
import cn.hoxise.module.movie.service.movie.bangumi.BangumiDbService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * @author hoxise
 * @since 2026/1/28 上午9:51
 */
@RestController
public class BangumiDbApiImpl implements BangumiDbApi {

    @Resource
    private BangumiDbService bangumiDbService;

    @Override
    public CommonResult<PageResult<BangumiDbDTO>> page(Integer pageNum, Integer pageSize) {
        Page<BangumiDbDO> page = bangumiDbService.page(new Page<>(pageNum, pageSize));
        List<BangumiDbDTO> dtoList = BeanUtil.copyToList(page.getRecords(), BangumiDbDTO.class);
        return CommonResult.success(new PageResult<>(dtoList, page.getPages()));
    }

    @Override
    public CommonResult<List<BangumiDbDTO>> list(Collection<Long> catalogids) {
        List<BangumiDbDO> list = bangumiDbService.list(Wrappers.lambdaQuery(BangumiDbDO.class)
                .in(catalogids != null && !catalogids.isEmpty(), BangumiDbDO::getCatalogid, catalogids));
        return CommonResult.success(BeanUtil.copyToList(list, BangumiDbDTO.class));
    }

    @Override
    public CommonResult<Long> count() {
        return CommonResult.success(bangumiDbService.count());
    }

}
