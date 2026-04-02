package cn.hoxise.module.system.controller.auth;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hoxise.common.base.utils.servlet.ServletUtil;
import cn.hoxise.common.security.operatelog.core.annotations.OperateLog;
import cn.hutool.core.util.StrUtil;
import com.xingyuv.captcha.model.common.ResponseModel;
import com.xingyuv.captcha.model.vo.CaptchaVO;
import com.xingyuv.captcha.service.CaptchaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码
 *
 * @author hoxise
 * @since 2026/01/14 06:09:57
 */
@Tag(name = "验证码")
@RestController
@RequestMapping("/system/captcha")
public class CaptchaController {

    @Resource
    private CaptchaService captchaService;

    @OperateLog(enable = false)
    @Operation(summary = "获得验证码")
    @PostMapping({"/get"})
    @SaIgnore
    public ResponseModel get(@RequestBody CaptchaVO data, HttpServletRequest request) {
        assert request.getRemoteHost() != null;
        data.setBrowserInfo(getRemoteId(request));
        return captchaService.get(data);
    }

    @OperateLog(enable = false)
    @Operation(summary = "校验验证码")
    @PostMapping("/check")
    @SaIgnore
    public ResponseModel check(@RequestBody CaptchaVO data, HttpServletRequest request) {
        data.setBrowserInfo(getRemoteId(request));
        return captchaService.check(data);
    }

    public static String getRemoteId(HttpServletRequest request) {
        String ip = ServletUtil.getClientIp(request);
        String ua = request.getHeader("user-agent");
        if (StrUtil.isNotBlank(ip)) {
            return ip + ua;
        }
        return request.getRemoteAddr() + ua;
    }

}
