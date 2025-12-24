package cn.hoxise.self.biz.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author hoxise
 * @Description: 目录状态枚举
 * @Date 2025-08-13 上午12:11
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
