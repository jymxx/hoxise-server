package cn.hoxise.self.biz.pojo.constants;

/**
 * @Author hoxise
 * @Description: Redis常量
 * @Date 2025-12-22 下午5:50
 */
public class MovieRedisConstants {

    //缓存TMDB查询结果
    public final static String TMDB_SEARCHMULTI_KEY = "TMDBsearchMulti";

    //统计分析影视总数
    public final static String MOVIE_STAT_KEY = "MovieStatKey";

    //给影视库全量查询的缓存
    public final static String MOVIE_LIBRARY_KEY = "MovieLibraryKey";

}
