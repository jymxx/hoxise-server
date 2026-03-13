package cn.hoxise.module.movie.api;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.movie.api.dto.BangumiDbCharacterRespDTO;
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
 * 角色
 *
 * @author hoxise
 * @since 2026/1/28 下午12:35
 */
@FeignClient(name = RpcConstants.NAME)
@Tag(name = "Bangumi 角色")
public interface BangumiDbCharacterApi {

    String PREFIX = RpcConstants.API_PREFIX + "/bangumi/character";

    @Operation(summary = "获取角色信息数据")
    @GetMapping(PREFIX + "/list")
    @Parameters({
            @Parameter(name = "catalogIds", description = "过滤目录id", example = "[1,2,3]", required = true)
    })
    CommonResult<List<BangumiDbCharacterRespDTO>> list(@RequestParam("catalogIds") Collection<Long> catalogIds);
}
