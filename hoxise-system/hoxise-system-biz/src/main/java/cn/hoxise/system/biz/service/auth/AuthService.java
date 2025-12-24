package cn.hoxise.system.biz.service.auth;


import cn.hoxise.system.biz.controller.auth.dto.AuthLoginDTO;
import cn.hoxise.system.biz.controller.auth.dto.AuthLoginSmsDTO;
import cn.hoxise.system.biz.controller.auth.vo.LoginResultVO;

/**
 * @Author: hoxise
 * @Description:
 * @Date: 2023/8/27 0:45
 */
public interface AuthService {

    /***
     * @Description: 登录操作
     * @return: com.hoxise.general.reponse.auth.LoginResultResponse
     * @author: hoxise
     * @date: 2023/8/27 2:03
     */
    LoginResultVO loginValidate(AuthLoginDTO authLoginDTO);

    void logout();

    /**
     * @Author: hoxise
     * @Description: 短信登录
     * @Date: 2025/12/12 下午10:14
     */
    LoginResultVO loginSms(AuthLoginSmsDTO authLoginSmsDTO);

}
