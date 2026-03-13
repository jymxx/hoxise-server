package cn.hoxise.module.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统角色枚举
 * 因为不打算在权限上做太深入，目前简单与数据库对应即可
 *
 * @author hoxise
 * @since 2026/01/14 06:13:54
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {

    ADMIN(0,"admin", "超级管理员"),
    MANAGER(1,"manager", "管理员"),
    USER(2,"user", "普通用户");

    private final Integer code;
    private final String name;
    private final String description;
}
