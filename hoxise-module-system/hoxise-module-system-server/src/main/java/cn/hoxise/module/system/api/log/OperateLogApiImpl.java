package cn.hoxise.module.system.api.log;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.system.api.log.dto.OperateLogCreateReqDTO;
import cn.hoxise.module.system.service.log.OperateLogService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

/**
 * OperateLogApiImpl
 *
 * @author hoxise
 * @since 2026/01/14 06:09:05
 */
@RestController
@Validated
public class OperateLogApiImpl implements OperateLogApi {

    @Resource private OperateLogService operateLogService;

    @Override
    public CommonResult<Boolean> createOperateLog(OperateLogCreateReqDTO operateLog) {
        operateLogService.createOperateLog(operateLog);
        return CommonResult.success(true);
    }
}
