package cn.hoxise.self.movie.pojo.enums.bangumi;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 国家地区枚举
 *
 * @author hoxise
 * @since 2026/01/14 15:00:58
 */
@Getter
@AllArgsConstructor
public enum MovieCountyEnum {

    unknown("unknown","未知"),
    zh("cn","中国"),
    en("us","美国"),
    jp("jp","日本"),
    ja("ja","日本");

    private final String shortName;
    private final String name;

    public static MovieCountyEnum getNameByShortName(String shortName){
        for (MovieCountyEnum value : values()) {
            if (value.shortName.equalsIgnoreCase(shortName)){
                return value;
            }
        }
        return unknown;
    }

}