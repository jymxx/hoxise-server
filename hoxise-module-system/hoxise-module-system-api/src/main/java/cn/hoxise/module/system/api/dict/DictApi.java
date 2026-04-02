package cn.hoxise.module.system.api.dict;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.system.enums.RpcConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 暴露给其它模块的字典接口
 *
 * @author hoxise
 * @since 2026/01/14 06:11:24
 */
@FeignClient(name = RpcConstants.NAME, contextId = "DictApi")
@Tag(name = "RPC 字典接口")
public interface DictApi {

    String PREFIX = RpcConstants.API_PREFIX + "/dict";

    @GetMapping(PREFIX + "/getByKey")
    @Operation(summary = "获取字典")
    @Parameter(name = "key", description = "字典key", example = "", required = true)
    CommonResult<String> getByKey(@RequestParam("key") String key);
}
