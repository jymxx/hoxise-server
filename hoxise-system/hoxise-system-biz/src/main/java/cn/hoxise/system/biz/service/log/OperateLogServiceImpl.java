package cn.hoxise.system.biz.service.log;

import cn.hoxise.system.api.log.dto.OperateLogCreateReqDTO;
import cn.hoxise.system.biz.convert.OperateLogConvert;
import cn.hoxise.system.biz.dal.entity.OperateLogDO;
import cn.hoxise.system.biz.dal.mapper.OperateLogMapper;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static cn.hoxise.system.biz.dal.entity.OperateLogDO.JAVA_METHOD_ARGS_MAX_LENGTH;
import static cn.hoxise.system.biz.dal.entity.OperateLogDO.RESULT_MAX_LENGTH;

/**
 * 操作日志 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLogDO> implements OperateLogService {

    @Override
    public void createOperateLog(OperateLogCreateReqDTO createReqDTO) {
        OperateLogDO log = OperateLogConvert.INSTANCE.convert(createReqDTO);
        log.setJavaMethodArgs(StrUtil.maxLength(log.getJavaMethodArgs(), JAVA_METHOD_ARGS_MAX_LENGTH));
        log.setResultData(StrUtil.maxLength(log.getResultData(), RESULT_MAX_LENGTH));
        baseMapper.insert(log);
    }

}
