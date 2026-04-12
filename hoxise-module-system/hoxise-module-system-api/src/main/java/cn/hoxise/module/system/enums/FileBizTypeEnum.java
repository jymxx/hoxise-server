package cn.hoxise.module.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件业务类型枚举
 *
 * @author hoxise
 * @since 2026/04/12
 */
@Getter
@AllArgsConstructor
public enum FileBizTypeEnum {

    AVATAR("avatar", "用户头像", "user/avatar");

    private final String type;
    private final String description;
    private final String ossDir;

}