package cn.hoxise.system.biz.service.sms;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hoxise.system.biz.dal.entity.SystemSmsLogDO;
import cn.hoxise.system.biz.dal.mapper.SystemSmsLogMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
* @author Hoxise
* @description 针对表【system_sms】的数据库操作Service实现
* @createDate 2025-12-12 22:06:19
*/
@Service
public class SystemSmsLogServiceImpl extends ServiceImpl<SystemSmsLogMapper, SystemSmsLogDO>
    implements SystemSmsLogService {

    @Override
    public void saveSendLog(String phone,String requestId,String type){
        this.save(SystemSmsLogDO.builder().phone(phone).requestId(requestId).type(type).sendTime(LocalDateTime.now()).build());
    }
}




