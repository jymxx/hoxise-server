package cn.hoxise.common.log.service;

import org.springframework.scheduling.annotation.Async;

/**
 * 操作日志 Framework Service 接口
 *
 * @author 芋道源码
 */
public interface OperateLogBaseService {

    /**
     * 记录操作日志
     *
     * @param  reqDTO 操作日志请求
     */
    @Async
    void createOperateLog(OperateLogBaseDTO reqDTO);
}
