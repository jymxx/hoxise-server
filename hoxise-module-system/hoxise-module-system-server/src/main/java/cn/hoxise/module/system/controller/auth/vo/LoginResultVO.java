package cn.hoxise.module.system.controller.auth.vo;

import cn.hoxise.module.system.controller.user.vo.UserInfoVO;
import cn.hoxise.module.system.controller.user.vo.UserInfoVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 登录结果
 *
 * @author hoxise
 * @since 2026/01/14 06:05:22
 */
@Data
@Builder
@Schema(name = "用户登录结果")
public class LoginResultVO {

    @Schema(name = "登录状态",example = "success/failure")
    private String status;

    @Schema(name = "时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime loginTime;

    @Schema(name = "token")
    private String token;

    @Schema(name = "用户信息",type = "UserInfoResponse")
    private UserInfoVO userInfo;

}
