package cn.hoxise.system.biz.api.log;

import cn.hoxise.system.api.log.OperateLogApi;
import cn.hoxise.system.api.log.dto.OperateLogCreateReqDTO;
import cn.hoxise.system.biz.service.log.OperateLogService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author hoxise
 * @Description: 操作日志接口实现类
 * @Date 2026-01-04 上午7:45
 */
@Service
public class OperateLogApiImpl implements OperateLogApi {

    @Resource private OperateLogService operateLogService;
    @Override
    public void createOperateLog(OperateLogCreateReqDTO operateLog) {
        operateLogService.createOperateLog(operateLog);
    }
}
