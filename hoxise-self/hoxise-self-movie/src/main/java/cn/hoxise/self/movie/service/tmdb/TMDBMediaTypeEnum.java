package cn.hoxise.self.movie.service.tmdb;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author hoxise
 * @Description: TMDB数据库影视类型
 * @Date 2025-12-22 下午1:22
 */
@Getter
@AllArgsConstructor
public enum TMDBMediaTypeEnum {

    TV("tv", "电视剧/动漫"),
    Movie("movie", "电影/动漫电影");

    private final String name;
    private final String desc;

    public static TMDBMediaTypeEnum getByName(String name){
        for (TMDBMediaTypeEnum value : values()) {
            if (value.name.equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }
}
