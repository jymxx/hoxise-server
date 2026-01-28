package cn.hoxise.module.movie.service.tmdb;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TMDB数据库影视类型
 *
 * @author hoxise
 * @since 2026/01/14 15:02:01
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
