package cn.hoxise.module.system.controller.auth;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.common.security.operatelog.core.annotations.OperateLog;
import cn.hoxise.module.system.controller.auth.dto.AuthLoginSmsDTO;
import cn.hoxise.module.system.controller.auth.vo.LoginResultVO;
import cn.hoxise.module.system.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证操作
 *
 * @author hoxise
 * @since 2026/1/14 上午5:26
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

    @OperateLog(enable = false)
    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    public CommonResult<Boolean> logout() {
        authService.logout();
        return CommonResult.success(true);
    }

    @OperateLog(enable = false)
    @Operation(summary = "短信登录")
    @PostMapping("/loginSms")
    @SaIgnore
    public CommonResult<LoginResultVO> loginSms(@Validated @RequestBody AuthLoginSmsDTO authLoginSmsDTO) {
        return CommonResult.success(authService.loginSms(authLoginSmsDTO));
    }

}
