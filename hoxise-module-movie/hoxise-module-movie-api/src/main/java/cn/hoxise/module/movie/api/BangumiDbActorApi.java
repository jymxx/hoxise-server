package cn.hoxise.module.movie.api;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.movie.api.dto.BangumiDbActorDTO;
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
 * 演员/CV
 *
 * @author hoxise
 * @since 2026/1/28 下午12:54
 */
@FeignClient(name = RpcConstants.NAME)
@Tag(name = "Bangumi 演员/声优")
public interface BangumiDbActorApi {

    String PREFIX = RpcConstants.API_PREFIX + "/bangumi/actor";

    @Operation(summary = "获取演员数据")
    @GetMapping(PREFIX + "/list")
    @Parameters({
            @Parameter(name = "actorids", description = "过滤演员id", example = "[1,2,3]", required = true)
    })
    CommonResult<List<BangumiDbActorDTO>> list(@RequestParam("actorids") Collection<Long> actorids);
}
