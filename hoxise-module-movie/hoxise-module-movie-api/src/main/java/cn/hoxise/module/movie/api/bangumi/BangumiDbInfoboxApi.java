package cn.hoxise.module.movie.api.bangumi;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.movie.api.bangumi.dto.BangumiDbInfoboxDTO;
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
 * 信息框
 *
 * @author hoxise
 * @since 2026/1/28 上午10:28
 */
@FeignClient(name = RpcConstants.NAME)
@Tag(name = "RPC 影视DB信息框数据")
public interface BangumiDbInfoboxApi {

    String PREFIX =  RpcConstants.API_PREFIX + "/bangumi/infobox";

    @Operation(summary = "获取信息框数据")
    @GetMapping(PREFIX + "/list")
    @Parameters({
            @Parameter(name = "catalogids", description = "过滤目录id", example = "[1,2,3]", required = true)
    })
    CommonResult<List<BangumiDbInfoboxDTO>> list(@RequestParam("catalogids") Collection<Long> catalogids);
}
