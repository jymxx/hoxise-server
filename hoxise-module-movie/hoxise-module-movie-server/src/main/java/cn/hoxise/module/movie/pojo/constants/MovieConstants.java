package cn.hoxise.module.movie.pojo.constants;

import java.util.regex.Pattern;

/**
 * 影视常量
 *
 * @author hoxise
 * @since 2026/01/14 14:59:01
 */
public class MovieConstants {

    /** Bangumi API番剧图片存储目录 **/
    public static final String BANGUMI_MINIO_FLODER = "MovieImg-Bangumi";

    /** 不匹配的字符 **/
    public static final Pattern MOVIE_CLEAN_PATTERN = Pattern.compile("❤|系列|合集");

    /** 只匹配英文字符 **/
    public static final Pattern MOVIE_CLEAN_ONLY_EN_PATTERN = Pattern.compile("[^a-zA-Z]");
}
