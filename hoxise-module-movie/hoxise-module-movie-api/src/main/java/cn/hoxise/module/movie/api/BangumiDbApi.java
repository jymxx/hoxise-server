package cn.hoxise.module.movie.api;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.common.base.pojo.PageResult;
import cn.hoxise.module.movie.api.dto.BangumiDbDTO;
import cn.hoxise.module.movie.enums.RpcConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * bangumi数据 Api
 *
 * @author hoxise
 * @since 2026/1/28 上午9:11
 */
@FeignClient(name = RpcConstants.NAME)
@Tag(name = "RPC 影视DB数据")
public interface BangumiDbApi {

    String PREFIX = RpcConstants.API_PREFIX + "/bangumi/db";

    @Operation(summary = "分页获取Bangumi DB数据")
    @GetMapping(PREFIX + "/page")
    @Parameters({
            @Parameter(name = "pageNum", description = "页码", example = "1", required = true),
            @Parameter(name = "pageSize", description = "每页数量", example = "10", required = true)
    })
    CommonResult<PageResult<BangumiDbDTO>> page(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);


    @Operation(summary = "获取Bangumi DB数据")
    @GetMapping(PREFIX + "/list")
    @Parameters({
            @Parameter(name = "catalogids", description = "过滤目录id", example = "[1,2,3]", required = true)
    })
    CommonResult<List<BangumiDbDTO>> list(@RequestParam("catalogids") Collection<Long> catalogids);

    @Operation(summary = "获取bangumi数据总数")
    @GetMapping(PREFIX + "/count")
    CommonResult<Long> count();
}
