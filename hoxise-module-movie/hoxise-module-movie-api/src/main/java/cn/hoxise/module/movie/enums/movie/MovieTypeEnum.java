package cn.hoxise.module.movie.enums.movie;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 影视类型枚举
 *
 * @author hoxise
 * @since 2026/01/14 15:01:15
 */
@Getter
@AllArgsConstructor
public enum MovieTypeEnum {

    other("other", "其它"),
    anime("anime", "动漫"),
    animeMovie("animeMovie", "动漫电影");


    @EnumValue
    private final String name;
    @JsonValue
    private final String desc;

    public static MovieTypeEnum getByName(String name) {
        for (MovieTypeEnum value : values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }
}
