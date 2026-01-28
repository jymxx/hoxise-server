package cn.hoxise.module.system.api.user;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.system.enums.RpcConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author hoxise
 * @since 2026/01/14 06:12:43
 */
@FeignClient(name = RpcConstants.NAME)
@Tag(name = "RPC 角色权限接口")
public interface RolePermissionApi {

    String PREFIX = RpcConstants.API_PREFIX + "/role";

    @Operation(summary = "获取用户的角色列表")
    @GetMapping(PREFIX + "/getRoleList")
    @Parameter(name = "userId", description = "用户ID",example = "1", required = true)
    CommonResult<List<String>> getRoleList(@RequestParam("userId") Long userId);

}
