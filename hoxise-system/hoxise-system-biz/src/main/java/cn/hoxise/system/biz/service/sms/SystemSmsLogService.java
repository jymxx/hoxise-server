package cn.hoxise.system.biz.service.sms;

import cn.hoxise.system.biz.dal.entity.SystemSmsLogDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * SystemSmsLogService
 *
 * @author Hoxise
 * @since 2026/01/14 def time = new Date().format('HH:mm')
 */
public interface SystemSmsLogService extends IService<SystemSmsLogDO> {

    /**
     * saveSendLog
     *
     * @param phone 手机号
     * @param requestId SMS请求ID
     * @param type 发送类型
     * @author hoxise
     * @since 2026/01/14 05:55:39
     */
    void saveSendLog(String phone, String requestId, String type);
}
