package cn.hoxise.system.biz.controller.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @Author: hoxise
 * @Description: 用户信息封装
 * @Date: 2023/8/27 0:32
 */
@Data
@Builder
@Schema(name = "用户信息封装")
public class UserInfoVO {

    @Schema(name = "用户id")
    private Long userId;

    @Schema(name = "用户名")
    private String userName;

    @Schema(name = "手机号")
    private String phoneNumber;

    @Schema(name = "角色")
    private List<String> roles;
}