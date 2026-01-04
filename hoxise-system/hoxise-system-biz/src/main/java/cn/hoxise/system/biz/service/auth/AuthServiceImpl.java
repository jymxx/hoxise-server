package cn.hoxise.system.biz.service.auth;

import cn.hoxise.common.base.enums.CommonStatusEnum;
import cn.hoxise.system.biz.controller.auth.dto.AuthLoginDTO;
import cn.hoxise.system.biz.controller.auth.dto.AuthLoginSmsDTO;
import cn.hoxise.system.biz.service.sms.SystemSmsService;
import cn.hoxise.common.ai.uitls.SaTokenUtil;
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
 * @Author: hoxise
 * @Description: 认证相关业务类
 * @Date: 2023/8/27 0:45
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
     * 检查用户状态
     * @param systemUserDO
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
     * 实际登录方法
     * @param systemUserDO
     * @return
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

    private void afterLogin(SystemUserDO systemUserDO) {
        systemUserDO.setLastLoginTime(LocalDateTime.now());//更新登录时间
        systemUserService.updateById(systemUserDO);
    }


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
