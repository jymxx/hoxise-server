package cn.hoxise.system.api.log;

import cn.hoxise.system.api.log.dto.OperateLogCreateReqDTO;

/**
 * @Author hoxise
 * @Description: 日志记录
 * @Date 2026-01-04 上午7:22
 */
public interface OperateLogApi {

    void createOperateLog(OperateLogCreateReqDTO operateLog);
}
