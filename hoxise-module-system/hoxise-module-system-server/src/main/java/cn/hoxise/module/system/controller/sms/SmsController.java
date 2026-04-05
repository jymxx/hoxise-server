package cn.hoxise.module.system.controller.sms;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.common.security.operatelog.core.annotations.OperateLog;
import cn.hoxise.module.system.service.sms.SystemSmsSendService;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 短信服务
 *
 * @author hoxise
 * @since 2026/01/14 06:10:31
 */
@Tag(name = "短信服务")
@RestController
@RequestMapping("/system/sms")
public class SmsController {

    @Resource private SystemSmsSendService systemSmsSendService;

    @OperateLog(enable = false)
    @Operation(summary = "发送短信验证码")
    @PostMapping("/send")
    @SaIgnore
    public CommonResult<Boolean> send(String phone) {
        systemSmsSendService.sendLoginVerifyCode(phone);
        return CommonResult.ok();
    }

}
