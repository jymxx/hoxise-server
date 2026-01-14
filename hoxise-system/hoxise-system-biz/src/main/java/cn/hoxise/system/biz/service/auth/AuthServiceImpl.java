package cn.hoxise.system.biz.service.auth;

import cn.hoxise.common.base.enums.CommonStatusEnum;
import cn.hoxise.system.biz.controller.auth.dto.AuthLoginDTO;
import cn.hoxise.system.biz.controller.auth.dto.AuthLoginSmsDTO;
import cn.hoxise.system.biz.service.sms.SystemSmsService;
import cn.hoxise.common.security.uitls.SaTokenUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hoxise.system.biz.controller.auth.vo.LoginResultVO;
import cn.hoxise.system.biz.controller.user.vo.UserInfoVO;
import cn.hoxise.system.biz.dal.entity.SystemUserDO;
import cn.hoxise.system.biz.service.user.SystemUserService;
import cn.hoxise.common.base.exception.ServiceException;
import com.xingyuv.captcha.model.common.ResponseModel;
import com.xingyuv.captcha.model.vo.CaptchaVO;
import com.xingyuv.captcha.service.CaptchaService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 认证业务
 *
 * @author hoxise
 * @since 2026/1/14 上午5:57
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private SystemUserService systemUserService;

    @Resource
    private CaptchaService captchaService;

    @Resource
    private SystemSmsService systemSmsService;

    @Value("${project.runMode}")
    private String runMode;


    @Override
    public LoginResultVO loginValidate(AuthLoginDTO authLoginDTO) {
        //校验验证码
        validateCaptcha(authLoginDTO.getCaptcha());

        SystemUserDO systemUserDO = systemUserService.queryByUsername(authLoginDTO.getUsername());
        //是否存在
        if (BeanUtil.isEmpty(systemUserDO)){
            throw new ServiceException("不存在的用户.");
        }
        //密码校验
        if (!systemUserDO.getPassword().equals(authLoginDTO.getPassword())) {
            throw new ServiceException("密码错误.");
        }
        //检查是否禁用
        checkStatus(systemUserDO);
        //登录
        return realLogin(systemUserDO);
    }


    @Override
    public LoginResultVO loginSms(AuthLoginSmsDTO authLoginSmsDTO) {
        if (!"dev".equals(runMode)) {
            //校验验证码
            boolean checked = systemSmsService.checkLoginVerifyCode(authLoginSmsDTO.getPhone(), authLoginSmsDTO.getVerifyCode());
            if (!checked){
                throw new ServiceException("验证码错误");
            }
        }

        SystemUserDO userDO = systemUserService.queryByPhoneNumber(authLoginSmsDTO.getPhone());
        if (userDO == null){
            //注册
            userDO = systemUserService.register(authLoginSmsDTO.getPhone());
        }

        checkStatus(userDO);
        return realLogin(userDO);
    }

    /**
     * 检查用户状态是否可用
     *
     * @param systemUserDO 用户do
     * @author hoxise
     * @since 2026/01/14 06:04:35
     */
    private void checkStatus(SystemUserDO systemUserDO){
        //是否禁用
        if (!CommonStatusEnum.ENABLE.equals(systemUserDO.getStatus())) {
            throw new ServiceException("用户已锁定,禁止登录.");
        }
    }

    @Override
    public void logout() {
        //退出登录 并且会踢人下线
        SaTokenUtil.logout();
    }

    /**
     * 实际的登录方法
     *
     * @param systemUserDO 用户do
     * @return 登录结果
     * @author hoxise
     * @since 2026/01/14 06:04:57
     */
    private LoginResultVO realLogin(SystemUserDO systemUserDO) {
        //使用sa-token框架登录生成token
        String token = SaTokenUtil.login(systemUserDO.getUserId());
        LoginResultVO success = LoginResultVO.builder()
                .status("success")
                .loginTime(LocalDateTime.now())
                .token(token)
                .userInfo(UserInfoVO.builder()
                        .userId(systemUserDO.getUserId())
                        .userName(systemUserDO.getUserName())
                        .phoneNumber(systemUserDO.getPhoneNumber())
                        .roles(systemUserDO.getRoleIds())
                        .build())
                .build();
        //登录后逻辑处理
        afterLogin(systemUserDO);
        return success;
    }

    /**
     * 登录后处理
     * 未来逻辑多了换成异步
     *
     * @param systemUserDO 用户do
     * @author hoxise
     * @since 2026/01/14 06:05:46
     */
    private void afterLogin(SystemUserDO systemUserDO) {
        //更新登录时间
        systemUserDO.setLastLoginTime(LocalDateTime.now());
        systemUserService.updateById(systemUserDO);
    }


    /**
     * validateCaptcha
     *
     * @param captcha 验证码
     * @author hoxise
     * @since 2026/01/14 06:06:19
     */
    private void validateCaptcha(String captcha) {
        // 开发模式不校验
        if ("dev".equals(runMode)) {
            return;
        }
        // 校验验证码
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(captcha);
        ResponseModel response = captchaService.verification(captchaVO);
        // 验证不通过
        if (!response.isSuccess()) {
            throw new ServiceException("验证码错误.");
        }
    }


}
