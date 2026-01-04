package cn.hoxise.common.log.service;

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
    public void createOperateLog(OperateLogBaseDTO reqDTO) {
        OperateLogCreateReqDTO req = BeanUtil.toBean(reqDTO, OperateLogCreateReqDTO.class);
        operateLogApi.createOperateLog(req);
    }

}
