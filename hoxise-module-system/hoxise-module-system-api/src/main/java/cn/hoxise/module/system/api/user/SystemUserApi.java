package cn.hoxise.module.system.api.user;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.system.api.user.dto.UserInfoRespDTO;
import cn.hoxise.module.system.enums.RpcConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 用户API
 *
 * @author hoxise
 * @since 2026/4/2 下午11:31
 */
@FeignClient(name = RpcConstants.NAME,contextId = "SystemUserApi")
@Tag(name = "RPC 用户接口接口")
public interface SystemUserApi {

    String PREFIX = RpcConstants.API_PREFIX + "/user";

    @Operation(summary = "获取用户信息")
    @GetMapping(PREFIX + "/getUserById")
    @Parameter(name = "userId", description = "用户ID",example = "1", required = true)
    CommonResult<UserInfoRespDTO> getUserById(@RequestParam("userId") Long userId);
}
