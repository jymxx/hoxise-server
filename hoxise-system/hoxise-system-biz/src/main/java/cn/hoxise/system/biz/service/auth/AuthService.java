package cn.hoxise.system.biz.service.auth;


import cn.hoxise.system.biz.controller.auth.dto.AuthLoginDTO;
import cn.hoxise.system.biz.controller.auth.dto.AuthLoginSmsDTO;
import cn.hoxise.system.biz.controller.auth.vo.LoginResultVO;


/**
 * 认证服务
 *
 * @author hoxise
 * @since 2026/01/14 15:26:50
 */
public interface AuthService {

    /**
     * 登录操作
     *
     * @param authLoginDTO 认证参数
     * @return 登录结果
     * @author hoxise
     * @since 2026/01/14 15:28:24
     */
    LoginResultVO loginValidate(AuthLoginDTO authLoginDTO);

    /**
     * 登出
     *
     * @author hoxise
     * @since 2026/01/14 15:28:08
     */
    void logout();

    /**
     * 短信登录
     *
     * @param authLoginSmsDTO 短信认证参数
     * @return 登录结果
     * @author hoxise
     * @since 2026/01/14 15:28:42
     */
    LoginResultVO loginSms(AuthLoginSmsDTO authLoginSmsDTO);

}
