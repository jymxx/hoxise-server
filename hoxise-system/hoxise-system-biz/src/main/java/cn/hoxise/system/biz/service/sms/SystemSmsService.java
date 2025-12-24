package cn.hoxise.system.biz.service.sms;

/**
 * @Author hoxise
 * @Description: 短信接口
 * @Date 2025-12-12 下午10:20
 */
public interface SystemSmsService {
    void sendLoginVerifyCode(String mobile);

    boolean checkLoginVerifyCode(String mobile, String code);
}
