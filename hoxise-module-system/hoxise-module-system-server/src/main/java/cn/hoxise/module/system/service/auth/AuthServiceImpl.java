package cn.hoxise.module.system.service.auth;

import cn.dev33.satoken.stp.StpUtil;
import cn.hoxise.common.base.enums.CommonStatusEnum;
import cn.hoxise.module.system.controller.auth.dto.AuthLoginDTO;
import cn.hoxise.module.system.controller.auth.dto.AuthLoginSmsDTO;
import cn.hoxise.module.system.controller.user.vo.UserInfoVO;
import cn.hoxise.module.system.dal.entity.SystemUserDO;
import cn.hoxise.module.system.service.user.SystemUserService;
import cn.hoxise.module.system.service.sms.SystemSmsService;
import cn.hutool.core.bean.BeanUtil;
import cn.hoxise.module.system.controller.auth.vo.LoginResultVO;
import cn.hoxise.common.base.exception.ServiceException;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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

    @Value("${hoxise.runMode}")
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
        //是否可用
        if (!CommonStatusEnum.ENABLE.getStatus().equals(systemUserDO.getStatus())) {
            throw new ServiceException("用户已锁定,禁止登录.");
        }
    }

    @Override
    public void logout() {
        //退出登录 并且会踢人下线
        StpUtil.logout();
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
        StpUtil.login(systemUserDO.getUserId());
        String token = StpUtil.getTokenValue();

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
        systemUserService.update(Wrappers.lambdaUpdate(SystemUserDO.class)
                .eq(SystemUserDO::getUserId, systemUserDO.getUserId())
                .set(SystemUserDO::getLastLoginTime, LocalDateTime.now()));
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
