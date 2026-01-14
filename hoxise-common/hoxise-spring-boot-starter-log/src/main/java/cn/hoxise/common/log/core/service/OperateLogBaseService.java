package cn.hoxise.common.log.core.service;

import org.springframework.scheduling.annotation.Async;

/**
 * OperateLogBaseService
 *
 * @author hoxsise
 * @since 2026/01/14 07:11:15
 */
public interface OperateLogBaseService {


    /**
     * 创建操作日志
     *
     * @param reqDTO reqDTO
     * @param tokenValue 用户token
     * @author hoxise
     * @since 2026/01/14 07:11:43
     */
    @Async
    void createOperateLog(OperateLogBaseDTO reqDTO,String tokenValue);
}
