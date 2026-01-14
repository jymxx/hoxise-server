package cn.hoxise.system.api.log;

import cn.hoxise.system.api.log.dto.OperateLogCreateReqDTO;

/**
 * 暴露给其它模块的日志接口
 *
 * @author hoxise
 * @since 2026/01/14 06:11:36
 */
public interface OperateLogApi {

    /**
     * 创建操作日志
     *
     * @param operateLog 操作日志
     * @author hoxise
     * @since 2026/01/14 06:13:05
     */
    void createOperateLog(OperateLogCreateReqDTO operateLog);
}
