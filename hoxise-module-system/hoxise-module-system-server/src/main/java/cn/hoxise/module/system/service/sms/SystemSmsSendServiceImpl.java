package cn.hoxise.module.system.service.sms;

import cn.hoxise.common.base.exception.ServiceException;
import cn.hoxise.module.system.pojo.constants.RedisConstants;
import cn.hutool.core.lang.Assert;
import com.aliyun.dypnsapi20170525.models.SendSmsVerifyCodeResponseBody;
import jakarta.annotation.Resource;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * SystemSmsSendServiceImpl
 *
 * @author hoxise
 * @since 2026/01/14 05:53:53
 */
@Service
public class SystemSmsSendServiceImpl implements SystemSmsSendService {

    @Resource
    private SystemSmsLogService systemSmsLogService;

    @Resource
    private RedissonClient redissonClient;

    @Override
    public void sendLoginVerifyCode(String mobile) {
        Assert.notBlank(mobile, "手机号码不能为空");
        rateLimit(mobile);
        sendVerifyCodeAliyun(mobile);
    }

    @Override
    public boolean checkLoginVerifyCode(String mobile, String code) {
        try{
            return checkVerifyCodeAliyun(mobile,code);
        }catch (Exception error){
            throw new ServiceException("短信登录失败,请检查验证码是否正确");
        }
    }

    private void rateLimit(String mobile){
        //限流
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(RedisConstants.SMS_SEND_LIMIT_KEY+"::"+ mobile);
        rateLimiter.trySetRate(RateType.OVERALL,1, Duration.ofMinutes(1));//一分钟一次 不过好像在第60S就会重置？
        if (!rateLimiter.tryAcquire()){
            throw new ServiceException("短信发送太频繁");
        }
    }


    // ################ 暂时只有阿里云 ############ //

    private void sendVerifyCodeAliyun(String mobile) {
        SendSmsVerifyCodeResponseBody body  = AliyunSmsClient.sendVerifyCodeAliyun(mobile);
        //保存日志
        if (body.getSuccess()){
            String code = body.getModel().getRequestId();
            systemSmsLogService.saveSendLog(mobile,code,"验证码(阿里云)");
        }else{
            throw new ServiceException("短信发送失败:"+body.getMessage());
        }
    }

    private boolean checkVerifyCodeAliyun(String mobile,String code) {
        return AliyunSmsClient.checkVerifyCodeAliyun(mobile,code);
    }
}

