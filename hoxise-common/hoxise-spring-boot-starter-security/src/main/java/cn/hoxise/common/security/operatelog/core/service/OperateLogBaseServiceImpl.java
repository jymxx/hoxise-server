package cn.hoxise.common.security.operatelog.core.service;

import cn.hoxise.module.system.api.log.dto.OperateLogCreateReqDTO;
import cn.hoxise.module.system.api.log.OperateLogApi;
import cn.hutool.core.bean.BeanUtil;
import jakarta.annotation.Resource;

/**
 * 操作日志实现
 *
 * @author hoxise
 * @since 2026/01/14 06:32:45
 */
public class OperateLogBaseServiceImpl implements OperateLogBaseService {

    @Resource
    private OperateLogApi operateLogApi;

    @Override
    public void createOperateLog(OperateLogBaseDTO reqDTO) {
        OperateLogCreateReqDTO req = BeanUtil.toBean(reqDTO, OperateLogCreateReqDTO.class);
        operateLogApi.asyncCreateOperateLog(req);
    }

}
