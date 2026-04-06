package cn.hoxise.module.movie.api;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.movie.api.dto.MovieSimpleCatalogRespDTO;
import cn.hoxise.module.movie.dal.entity.MovieCatalogDO;
import cn.hoxise.module.movie.service.MovieCatalogService;
import cn.hutool.core.bean.BeanUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 影视目录api
 *
 * @author hoxise
 * @since 2026/2/25 上午8:05
 */
@RestController
public class MovieCatalogApiImpl implements MovieCatalogApi {

    @Resource
    private MovieCatalogService movieCatalogService;

    @Override
    public CommonResult<List<MovieSimpleCatalogRespDTO>> listSimpleCatalog(Long userid) {
        List<MovieCatalogDO> list = movieCatalogService.lambdaQuery()
                .select(MovieCatalogDO::getId, MovieCatalogDO::getName)
                .eq(MovieCatalogDO::getUserId, userid)
                .list();
        return CommonResult.success(BeanUtil.copyToList(list, MovieSimpleCatalogRespDTO.class));
    }

}
