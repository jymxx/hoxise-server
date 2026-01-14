package cn.hoxise.system.biz.service.log;


import cn.hoxise.system.api.log.dto.OperateLogCreateReqDTO;
import cn.hoxise.system.biz.dal.entity.OperateLogDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 操作日志 Service 接口
 *
 * @author hxise
 */
public interface OperateLogService extends IService<OperateLogDO> {

    /**
     * createOperateLog 创建操作日志
     *
     * @param createReqDTO 操作日志请求
     * @author hoxise
     * @since 2026/01/14 06:03:08
     */
    void createOperateLog(OperateLogCreateReqDTO createReqDTO);

}
