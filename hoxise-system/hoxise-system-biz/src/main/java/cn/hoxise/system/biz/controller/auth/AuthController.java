package cn.hoxise.system.biz.controller.auth;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.system.biz.controller.auth.dto.AuthLoginDTO;
import cn.hoxise.system.biz.controller.auth.dto.AuthLoginSmsDTO;
import cn.hoxise.system.biz.controller.auth.vo.LoginResultVO;
import cn.hoxise.system.biz.service.auth.AuthService;
import cn.hoxise.system.biz.utils.satoken.SaTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: hoxise
 * @Description: 认证相关操作
 * @Date: 2023/8/27 0:21
 */
@Tag(name = "认证操作")
@RestController
@RequestMapping("/system/auth")
public class AuthController {

    @Resource private AuthService authService;

//    @Operation(summary = "登录")
//    @PostMapping("/login")
//    public CommonResult<LoginResultVO> login(@Validated AuthLoginDTO authLoginDTO) {
//        return CommonResult.success(authService.loginValidate(authLoginDTO));
//    }

    @Operation(summary = "退出登录")
    @RequestMapping("/logout")
    public CommonResult<Boolean> logout() {
        authService.logout();
        return CommonResult.success(true);
    }

    @Operation(summary = "短信登录")
    @PostMapping("/loginSms")
    public CommonResult<LoginResultVO> loginSms(@Validated AuthLoginSmsDTO authLoginSmsDTO) {
        return CommonResult.success(authService.loginSms(authLoginSmsDTO));
    }

    @Operation(summary = "验证是否登录")
    @RequestMapping("/isLogin")
    public CommonResult<Boolean> isLogin() {
        return CommonResult.success(SaTokenUtil.isLogin());
    }
}
