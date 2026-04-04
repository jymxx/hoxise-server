package cn.hoxise.module.system.controller.user.vo;

import cn.hoxise.common.base.json.annotation.PhoneMask;
import cn.hoxise.common.file.core.annotations.FilePathFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 用户信息封装
 *
 * @author hoxise
 * @since 2026/01/14 06:10:55
 */
@Data
@Builder
@Schema(name = "用户信息封装")
public class UserInfoVO {

    @Schema(name = "用户id")
    private Long userId;

    @Schema(name = "用户名")
    private String userName;

    @Schema(name = "昵称")
    private String nickName;

    @Schema(name = "手机号")
    @PhoneMask  // 手机号脱敏：138****5678
    private String phoneNumber;

    @Schema(name = "角色")
    private List<String> roles;

    @Schema(name = "头像地址")
    @FilePathFormat
    private String avatar;
}