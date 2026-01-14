package cn.hoxise.system.biz.service.sms;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hoxise.system.biz.dal.entity.SystemSmsLogDO;
import cn.hoxise.system.biz.dal.mapper.SystemSmsLogMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * SystemSmsLogServiceImpl
 *
 * @author Hoxise
 * @since 2026/01/14 05:53:44
 */
@Service
public class SystemSmsLogServiceImpl extends ServiceImpl<SystemSmsLogMapper, SystemSmsLogDO>
    implements SystemSmsLogService {

    @Override
    public void saveSendLog(String phone,String requestId,String type){
        this.save(SystemSmsLogDO.builder().phone(phone).requestId(requestId).type(type).sendTime(LocalDateTime.now()).build());
    }
}




