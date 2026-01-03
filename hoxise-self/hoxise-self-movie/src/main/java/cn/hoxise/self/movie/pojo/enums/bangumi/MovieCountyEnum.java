package cn.hoxise.self.movie.pojo.enums.bangumi;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author hoxise
 * @Description: 国家地区枚举
 * @Date 2025-12-22 下午9:23
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