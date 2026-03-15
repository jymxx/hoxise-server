package cn.hoxise.module.movie.pojo.constants;

/**
 * MovieRedisConstants
 *
 * @author hoxise
 * @since 2026/01/14 14:59:08
 */
public class RedisConstants {

    //统计分析影视总数
    public final static String MOVIE_STAT_KEY = "MovieStatKey";

    //最后更新
    public final static String MOVIE_LAST_UPDATE_KEY = "MovieLastUpdateKey";

    //给影视库全量查询的缓存
    public final static String MOVIE_LIBRARY_KEY = "MovieLibraryKey";

    //自动匹配锁
    public final static String MOVIE_AUTO_MATCH_LOCK_KEY = "MovieAutoMatchLockKey";

    //自动匹配速率限制：每天 3 次
    public final static String MOVIE_AUTO_MATCH_RATE_LIMIT_KEY = "MovieAutoMatchRateLimit";

}
