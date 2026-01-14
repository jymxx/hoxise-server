package cn.hoxise.system.biz.service.sms;

/**
 * SystemSmsService
 *
 * @author hoxise
 * @since 2026/01/14 05:53:59
 */
public interface SystemSmsService {
    /**
     * 发送登录短信验证码
     *
     * @param mobile 手机号
     * @author hoxise
     * @since 2026/01/14 05:54:03
     */
    void sendLoginVerifyCode(String mobile);

    /**
     * 检查验证码
     *
     * @param mobile 手机号
     * @param code 验证码
     * @return boolean
     * @author hoxise
     * @since 2026/01/14 05:54:24
     */
    boolean checkLoginVerifyCode(String mobile, String code);
}
