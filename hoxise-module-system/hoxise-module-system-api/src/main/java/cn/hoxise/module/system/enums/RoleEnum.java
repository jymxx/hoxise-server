package cn.hoxise.module.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统角色枚举
 *
 * @author hoxise
 * @since 2026/01/14 06:13:54
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {

    ADMIN(1,"admin", "超级管理员"),
    manager(2,"manager", "管理员"),
    USER(2,"member", "普通用户");

    private final Integer code;
    private final String name;
    private final String description;
}
