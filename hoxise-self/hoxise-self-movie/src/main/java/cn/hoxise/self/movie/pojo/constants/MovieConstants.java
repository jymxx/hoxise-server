package cn.hoxise.self.movie.pojo.constants;

import java.util.regex.Pattern;

/**
 * @Author hoxise
 * @Description: 影视常量
 * @Date 2025-12-22 下午3:02
 */
public class MovieConstants {

    /** TMDB图片存储目录 **/
    public static final String TMDB_MINIO_FLODER = "MovieImg-TMDB";

    /** Bangumi API番剧图片存储目录 **/
    public static final String BANGUMI_MINIO_FLODER = "MovieImg-Bangumi";

    /** 不匹配的字符 **/
    public static final Pattern MOVIE_CLEAN_PATTERN = Pattern.compile("❤|系列|合集");

    /** 只匹配英文字符 **/
    public static final Pattern MOVIE_CLEAN_ONLY_EN_PATTERN = Pattern.compile("[^a-zA-Z]");
}
