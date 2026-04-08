package cn.hoxise.module.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态枚举
 *
 * @author hoxise
 * @since 2026/4/8 下午4:02
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnum {

    ENABLE(0, "正常"),
    DISABLE(1, "禁用");

    /**
     * 状态值
     */
    private final Integer status;
    /**
     * 状态名
     */
    private final String name;

}
