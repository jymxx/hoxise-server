package cn.hoxise.system.biz.controller.auth.vo;

import cn.hoxise.system.biz.controller.user.vo.UserInfoVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author: hoxise
 * @Description: 登录结果DTO
 * @Date: 2023/8/27 0:24
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
