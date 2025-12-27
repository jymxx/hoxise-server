package cn.hoxise.self.biz.service.ai;

import cn.hoxise.common.base.exception.ServiceException;
import cn.hoxise.common.base.utils.redis.RedisUtil;
import cn.hoxise.self.biz.pojo.enums.AiMethodEnum;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hoxise.self.biz.dal.entity.AiRequestRecordDO;
import cn.hoxise.self.biz.dal.mapper.AiRequestRecordMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
* @author Hoxise
* @description 针对表【ai_request_record】的数据库操作Service实现
* @createDate 2025-12-25 15:05:41
*/
@Service
public class AiRequestRecordServiceImpl extends ServiceImpl<AiRequestRecordMapper, AiRequestRecordDO>
    implements AiRequestRecordService{


    @Override
    public void record(AiMethodEnum method, Long loginId) {
        AiRequestRecordDO recordDO = AiRequestRecordDO.builder()
                .userid(loginId)
                .method(method)
                .requestTime(LocalDateTime.now())
                .build();
        this.save(recordDO);
    }

    @Override
    public void aiRateLimit(Long userid) {
        //后门喵
        if(userid == 0L){
            return;
        }

        String key = "ai_rate_limit:" + LocalDate.now() + ":"  + userid ;
        //获取今日调用次数
        String countStr = RedisUtil.getValue(key);
        int count = countStr == null ? 0 : Integer.parseInt(countStr);
        // 每日限制50次
        if (count >= 50) {
            throw new ServiceException("今日调用次数已达上限");
        }
        if (count == 0) {
            RedisUtil.setValue(key,"1",24, TimeUnit.HOURS);
        } else {
            RedisUtil.increment(key);
        }
    }

}




