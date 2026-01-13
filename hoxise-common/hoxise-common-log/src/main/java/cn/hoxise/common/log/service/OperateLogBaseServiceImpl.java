package cn.hoxise.common.log.service;

import cn.dev33.satoken.context.mock.SaTokenContextMockUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hoxise.system.api.log.OperateLogApi;
import cn.hoxise.system.api.log.dto.OperateLogCreateReqDTO;
import cn.hutool.core.bean.BeanUtil;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 操作日志 Framework Service 实现类
 *
 * 基于 {@link OperateLogApi} 实现，记录操作日志
 *
 * @author 芋道源码
 */
@Service
public class OperateLogBaseServiceImpl implements OperateLogBaseService {

    @Resource
    private OperateLogApi operateLogApi;

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
