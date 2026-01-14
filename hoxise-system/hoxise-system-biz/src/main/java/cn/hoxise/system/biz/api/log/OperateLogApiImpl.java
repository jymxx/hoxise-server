package cn.hoxise.system.biz.api.log;

import cn.hoxise.system.api.log.OperateLogApi;
import cn.hoxise.system.api.log.dto.OperateLogCreateReqDTO;
import cn.hoxise.system.biz.service.log.OperateLogService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * OperateLogApiImpl
 *
 * @author hoxise
 * @since 2026/01/14 06:09:05
 */
@Service
public class OperateLogApiImpl implements OperateLogApi {

    @Resource private OperateLogService operateLogService;

    @Override
    public void createOperateLog(OperateLogCreateReqDTO operateLog) {
        operateLogService.createOperateLog(operateLog);
    }
}
