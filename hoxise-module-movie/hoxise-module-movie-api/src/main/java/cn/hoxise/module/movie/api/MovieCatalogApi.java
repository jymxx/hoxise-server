package cn.hoxise.module.movie.api;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.movie.api.dto.MovieSimpleCatalogDTO;
import cn.hoxise.module.movie.enums.RpcConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 目录api
 *
 * @author hoxise
 * @since 2026/2/25 上午7:58
 */
@FeignClient(name = RpcConstants.NAME)
@Tag(name = "RPC 影视目录")
public interface MovieCatalogApi {

    String PREFIX = RpcConstants.API_PREFIX + "/catalog";

    @Operation(summary = "获取用户的影视目录")
    @GetMapping(PREFIX + "/listSimpleCatalog")
    @Parameters({
            @Parameter(name = "userid", description = "用户id 为空时返回所有", example = "1", required = false),
    })
    CommonResult<List<MovieSimpleCatalogDTO>> listSimpleCatalog(@RequestParam("userid") Long userid);

}
