package cn.hoxise.module.movie.api;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.common.base.pojo.PageResult;
import cn.hoxise.module.movie.api.dto.BangumiDbRespDTO;
import cn.hoxise.module.movie.dal.entity.BangumiDbDO;
import cn.hoxise.module.movie.dal.entity.MovieCatalogDO;
import cn.hoxise.module.movie.service.MovieCatalogService;
import cn.hoxise.module.movie.service.bangumi.BangumiDbService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author hoxise
 * @since 2026/1/28 上午9:51
 */
@RestController
public class BangumiDbApiImpl implements BangumiDbApi {

    @Resource
    private MovieCatalogService movieCatalogService;

    @Resource
    private BangumiDbService bangumiDbService;

    @Override
    public CommonResult<PageResult<BangumiDbRespDTO>> page(Integer pageNum, Integer pageSize) {
        Page<BangumiDbDO> page = bangumiDbService.page(new Page<>(pageNum, pageSize));
        List<BangumiDbRespDTO> dtoList = BeanUtil.copyToList(page.getRecords(), BangumiDbRespDTO.class);
        return CommonResult.success(new PageResult<>(dtoList, page.getPages()));
    }

    @Override
    public CommonResult<List<BangumiDbRespDTO>> list(Collection<Long> catalogIds) {
        List<MovieCatalogDO> catalogs = movieCatalogService.list(Wrappers.lambdaQuery(MovieCatalogDO.class).select(MovieCatalogDO::getBangumiId).in(MovieCatalogDO::getId, catalogIds));
        if (catalogs.isEmpty()) {
            return CommonResult.success(new ArrayList<>());
        }
        List<Long> bangumiIds = catalogs.stream().map(MovieCatalogDO::getBangumiId).toList();
        //db数据
        List<BangumiDbDO> list = bangumiDbService.list(Wrappers.lambdaQuery(BangumiDbDO.class)
                .in(!bangumiIds.isEmpty(), BangumiDbDO::getBangumiId, bangumiIds));
        return CommonResult.success(BeanUtil.copyToList(list, BangumiDbRespDTO.class));
    }

    @Override
    public CommonResult<Long> count() {
        return CommonResult.success(bangumiDbService.count());
    }

}
