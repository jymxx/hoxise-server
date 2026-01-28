package cn.hoxise.module.ai.service;

import cn.hoxise.common.base.exception.ServiceException;
import cn.hoxise.module.ai.dal.entity.AiRequestRecordDO;
import cn.hoxise.module.ai.dal.mapper.AiRequestRecordMapper;
import cn.hoxise.module.ai.pojo.constants.RedisConstants;
import cn.hoxise.module.ai.pojo.enums.AiMethodEnum;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 请求记录服务实现类
 *
 * @author Hoxise
 * @since 2026/01/14 14:52:59
 */
@Service
public class AiRequestRecordServiceImpl extends ServiceImpl<AiRequestRecordMapper, AiRequestRecordDO>
    implements AiRequestRecordService{

    @Resource
    private RedissonClient redissonClient;

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
        if(userid == 0L){
            return;
        }
        //限制请求频率
        String rateLimitKey = RedisConstants.AI_REQUEST_LIMIT_KEY + "::" + userid ;
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(rateLimitKey);
        //每小时限制100次
        rateLimiter.trySetRate(RateType.OVERALL,100, Duration.ofHours(1));

        if (!rateLimiter.tryAcquire()){
            throw new ServiceException("请勿频繁调用");
        }
    }

}




