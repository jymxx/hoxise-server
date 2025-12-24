package cn.hoxise.system.biz.service.sms;

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
        String code = AliyunSmsClient.sendVerifyCodeAliyun(mobile);
        systemSmsLogService.saveSendLog(mobile,code,"验证码(阿里云)");
    }

    private boolean checkVerifyCodeAliyun(String mobile,String code) {
        return AliyunSmsClient.checkVerifyCodeAliyun(mobile,code);
    }
}

