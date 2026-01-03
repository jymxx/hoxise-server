//package cn.hoxise.common.log.service;
//
//import cn.hoxise.common.log.annotations.OperateLog;
//import cn.hutool.core.bean.BeanUtil;
//import cn.iocoder.yudao.module.system.api.logger.OperateLogApi;
//import cn.iocoder.yudao.module.system.api.logger.dto.OperateLogCreateReqDTO;
//import lombok.RequiredArgsConstructor;
//import org.springframework.scheduling.annotation.Async;
//
///**
// * 操作日志 Framework Service 实现类
// *
// * 基于 {@link OperateLogApi} 实现，记录操作日志
// *
// * @author 芋道源码
// */
//@RequiredArgsConstructor
//public class OperateLogServiceImpl implements OperateLogService {
//
//    private final OperateLogApi operateLogApi;
//
//    @Override
//    @Async
//    public void createOperateLog(OperateLog operateLog) {
//        OperateLogCreateReqDTO reqDTO = BeanUtil.toBean(operateLog, OperateLogCreateReqDTO.class);
//        operateLogApi.createOperateLog(reqDTO);
//    }
//
//}
