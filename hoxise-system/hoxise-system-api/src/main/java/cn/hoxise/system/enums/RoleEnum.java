package cn.hoxise.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author hoxise
 * @Description: 角色枚举 与数据库对应 因为本系统暂不需要复杂权限设计
 * @Date 2025-12-29 上午9:00
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {

    ADMIN(1,"admin", "超级管理员"),
    manager(2,"manager", "管理员"),
    USER(2,"member", "普通用户");

    @EnumValue
    private final Integer code;
    private final String name;
    @JsonValue
    private final String description;
}
