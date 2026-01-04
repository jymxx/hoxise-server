package cn.hoxise.system.biz.service.log;


import cn.hoxise.system.api.log.dto.OperateLogCreateReqDTO;
import cn.hoxise.system.biz.dal.entity.OperateLogDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 操作日志 Service 接口
 *
 * @author 芋道源码
 */
public interface OperateLogService extends IService<OperateLogDO> {

    /**
     * 记录操作日志
     *
     * @param createReqDTO 操作日志请求
     */
    void createOperateLog(OperateLogCreateReqDTO createReqDTO);

}
