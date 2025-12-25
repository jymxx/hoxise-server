package cn.hoxise.system.biz.service.sms;

import cn.hoxise.common.base.exception.ServiceException;
import com.aliyun.dypnsapi20170525.models.SendSmsVerifyCodeResponseBody;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author hoxise
 * @Description: 短信发送逻辑
 * @Date 2025-12-12 下午10:20
 */
@Service
public class SystemSmsSendServiceImpl implements SystemSmsService{

    @Resource
    private SystemSmsLogService systemSmsLogService;

    @Override
    public void sendLoginVerifyCode(String mobile) {
        sendVerifyCodeAliyun(mobile);
    }

    @Override
    public boolean checkLoginVerifyCode(String mobile, String code) {
        return checkVerifyCodeAliyun(mobile,code);
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

