package cn.hoxise.module.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件绑定状态枚举
 *
 * @author hoxise
 * @since 2026/04/12
 */
@Getter
@AllArgsConstructor
public enum FileBindStatusEnum {

    UNBIND(0, "未绑定"),
    BIND(1, "已绑定");

    private final Integer status;
    private final String name;
}