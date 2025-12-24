package cn.hoxise.system.biz.service.auth;

import cn.hoxise.common.base.enums.CommonStatusEnum;
import cn.hoxise.system.biz.controller.auth.dto.AuthLoginDTO;
import cn.hoxise.system.biz.controller.auth.dto.AuthLoginSmsDTO;
import cn.hoxise.system.biz.service.sms.SystemSmsService;
import cn.hoxise.system.biz.utils.satoken.SaTokenUtil;
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
import java.util.Date;

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
        //是否禁用
        if (CommonStatusEnum.isDisable(systemUserDO.getStatus())) {
            throw new ServiceException("用户已锁定,禁止登录.");
        }
        //登录
        LoginResultVO loginResultVO = realLogin(systemUserDO);
        //登录后逻辑处理
        afterLogin(systemUserDO);
        return loginResultVO;
    }

    @Override
    public LoginResultVO loginSms(AuthLoginSmsDTO authLoginSmsDTO) {
        systemSmsService.checkLoginVerifyCode(authLoginSmsDTO.getPhone(), authLoginSmsDTO.getVerifyCode());
        return null;
    }

    @Override
    public void logout() {
        //退出登录 并且会踢人下线
        SaTokenUtil.logout();
    }

    private LoginResultVO realLogin(SystemUserDO systemUserDO) {
        //使用sa-token框架登录生成token
        String token = SaTokenUtil.login(systemUserDO.getUserId());
        return LoginResultVO.builder()
                .status("success")
                .loginTime(LocalDateTime.now())
                .token(token)
                .userInfo(UserInfoVO.builder()
                        .userId(systemUserDO.getUserId())
                        .userName(systemUserDO.getUserName())
                        .phoneNumber(systemUserDO.getPhoneNumber())
                        .role(systemUserDO.getRoleIds())
                        .build())
                .build();
    }

    private void afterLogin(SystemUserDO systemUserDO) {
        systemUserDO.setLastLoginTime(LocalDateTime.now());//更新登录时间
        systemUserService.updateById(systemUserDO);
    }


    private void validateCaptcha(String captcha) {
        // debug模式不校验
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
