package cn.hoxise.system.biz.service.sms;

import cn.hoxise.system.biz.dal.entity.SystemSmsLogDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Hoxise
* @description 针对表【system_sms】的数据库操作Service
* @createDate 2025-12-12 22:06:19
*/
public interface SystemSmsLogService extends IService<SystemSmsLogDO> {

    void saveSendLog(String phone, String requestId, String type);
}
