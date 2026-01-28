package cn.hoxise.module.system.api.log;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.system.api.log.dto.OperateLogCreateReqDTO;
import cn.hoxise.module.system.enums.RpcConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 暴露给其它模块的日志接口
 *
 * @author hoxise
 * @since 2026/01/14 06:11:36
 */
@FeignClient(name = RpcConstants.NAME)
@Tag(name = "RPC 操作日志接口")
public interface OperateLogApi{

    String PREFIX = RpcConstants.API_PREFIX + "/operate-log";

    @Operation(summary = "创建操作日志")
    @PostMapping(PREFIX + "/create")
    @Parameter(name = "operateLog", description = "操作日志DTO")
    CommonResult<Boolean> createOperateLog(@RequestBody OperateLogCreateReqDTO operateLog);


    /**
     * 异步创建操作日志
     *
     * @param operateLog 日志DTO
     * @author hoxise
     * @since 2026/01/26 21:50:56
     */
    @Async
    default void asyncCreateOperateLog(OperateLogCreateReqDTO operateLog) {
        //todo 改造MQ
        createOperateLog(operateLog);
    }
}
