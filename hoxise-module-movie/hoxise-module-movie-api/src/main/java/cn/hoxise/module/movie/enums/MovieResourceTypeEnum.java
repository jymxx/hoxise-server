package cn.hoxise.module.movie.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
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
public enum MovieResourceTypeEnum {

    OTHER_CLOUD("other_cloud", "其他云盘存储，直链"),
    VIDEO("video", "视频"),
    RESOURCE_FILE("resource_file", "资源文件");

    @EnumValue
    private final String code;
    private final String description;

    public static MovieResourceTypeEnum getByCode(String code) {
        for (MovieResourceTypeEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
