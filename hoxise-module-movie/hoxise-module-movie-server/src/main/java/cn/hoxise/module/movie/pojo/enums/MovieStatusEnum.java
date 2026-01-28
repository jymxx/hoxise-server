package cn.hoxise.module.movie.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 目录状态枚举
 *
 * @author hoxise
 * @since 2026/01/14 15:01:08
 */
@Getter
@AllArgsConstructor
public enum MovieStatusEnum {

    NORMAL(0, "正常"),
    EXPIRED(1, "过期"),
    Unknown(10, "未知");

    @EnumValue
    private final Integer code;
    @JsonValue
    private final String name;
}
