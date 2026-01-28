package cn.hoxise.module.system.controller.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 短信认证请求DTO
 *
 * @author hoxise
 * @since 2026/1/14 上午5:33
 */
@Data
public class AuthLoginSmsDTO {

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13888888888")
    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @Schema(description = "验证码，验证码开启时，需要传递", requiredMode = Schema.RequiredMode.REQUIRED,example = "1234")
    @NotEmpty(message = "验证码不能为空")
    private String verifyCode;
}
