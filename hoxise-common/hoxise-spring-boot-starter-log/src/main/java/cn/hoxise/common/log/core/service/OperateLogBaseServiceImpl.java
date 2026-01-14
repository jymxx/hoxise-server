package cn.hoxise.common.log.core.service;

import cn.dev33.satoken.context.mock.SaTokenContextMockUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hoxise.system.api.log.OperateLogApi;
import cn.hoxise.system.api.log.dto.OperateLogCreateReqDTO;
import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

/**
 * 操作日志实现
 *
 * @author hoxise
 * @since 2026/01/14 06:32:45
 */
@RequiredArgsConstructor
public class OperateLogBaseServiceImpl implements OperateLogBaseService {

    private final OperateLogApi operateLogApi;

    @Override
    @Async
    public void createOperateLog(OperateLogBaseDTO reqDTO, String tokenValue) {
        SaTokenContextMockUtil.setMockContext(()->{
            StpUtil.setTokenValueToStorage(tokenValue);//解决异步读不到用户信息的问题
            OperateLogCreateReqDTO req = BeanUtil.toBean(reqDTO, OperateLogCreateReqDTO.class);
            operateLogApi.createOperateLog(req);
        });
    }

}
