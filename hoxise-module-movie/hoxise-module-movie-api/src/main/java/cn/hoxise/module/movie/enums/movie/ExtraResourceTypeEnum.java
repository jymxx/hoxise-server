package cn.hoxise.module.movie.enums.movie;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 资源类型枚举
 *
 * @author hoxise
 * @since 2026/07/08
 */
@Getter
@AllArgsConstructor
public enum ExtraResourceTypeEnum {

    CLOUD_DRIVE("cloud_drive", "云盘链接"),
    VIDEO("video", "视频"),
    RESOURCE_FILE("resource_file", "资源文件");

    @EnumValue
    @JsonValue
    private final String code;
    private final String description;

    public static ExtraResourceTypeEnum getByCode(String code) {
        for (ExtraResourceTypeEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
