package cn.hoxise.module.system.api.user.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户信息响应
 *
 * @author hoxise
 * @since 2026/4/2 下午11:34
 */
@Data
public class UserInfoRespDTO {

    private Long userId;

    /** 用户名 */
    private String userName;

    /** 密码 */
    private String password;

    /** 昵称 */
    private String nickName;

    /** 最后登录时间 */
    private LocalDateTime lastLoginTime;

    private List<String> roleIds;

    /** 状态,0正常 1(禁止登录) */
    private Integer status;

    /** 头像 */
    private String avatar;
}
