package cn.hoxise.system.biz.controller.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @Author hoxise
 * @Description: 短信验证码登录DTO
 * @Date 2025-12-13 上午1:45
 */
@Schema(description = "Request DTO")
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
