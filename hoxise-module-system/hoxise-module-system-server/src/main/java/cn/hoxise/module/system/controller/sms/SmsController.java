package cn.hoxise.module.system.controller.sms;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.system.service.sms.SystemSmsService;
import cn.hoxise.module.system.service.sms.SystemSmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Resource private SystemSmsService systemSmsService;

    @Operation(summary = "发送短信验证码")
    @PostMapping("/send")
    public CommonResult<Boolean> send(String phone) {
        systemSmsService.sendLoginVerifyCode(phone);
        return CommonResult.ok();
    }

}
