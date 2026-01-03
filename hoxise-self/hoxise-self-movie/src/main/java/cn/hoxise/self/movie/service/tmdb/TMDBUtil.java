package cn.hoxise.self.movie.service.tmdb;

import cn.hoxise.common.base.exception.ServiceException;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @Author hoxise
 * @Description: TMDB工具
 * @Date 2025-12-22 上午9:51
 */
@Slf4j
@Component
public class TMDBUtil {

    //依赖Clash的代理 需要启动clash
    private static final String ProxyHost = "127.0.0.1";
    private static final Integer ProxyPort = 7890;
    //TMDB图片地址前缀
    private static final String TMDB_ImgPrefix = "https://image.tmdb.org/t/p/original";

    // 创建一个每秒允许0.5个请求的RateLimiter（即每分钟30次）
    private static final RateLimiter rateLimiter = RateLimiter.create(0.5);

    /**
     * @Author: hoxise
     * @Description: 关键字查询
     * @Date: 2025/12/22 上午10:41
     */
    public static TMDBMulitSearchResponse searchMulti(String keyword){
        rateLimiter.acquire();
        try{
            String body = HttpUtil.createGet("https://api.themoviedb.org/3/search/multi?page=1&language=zh-CN&query="+ keyword)
                    .setHttpProxy(ProxyHost, ProxyPort)
                    .header("accept", "application/json")
                    .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkMjk5MWNjZjExYTFhZTk2MTAzODc2NzI3NDcyZDE4MSIsIm5iZiI6MTc2NjM2NTQ5NS4xMTYsInN1YiI6IjY5NDg5OTM3MmNkMDhhMjUzOTA3MzJlZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.NEDRgJoal9LTk_1gYUzCupFB4WtlvEALFsNrXNpXj1A")
                    .execute()
                    .body();
            if (body != null){
                TMDBMulitSearchResponse response = JSONObject.parseObject(body, TMDBMulitSearchResponse.class);
                log.info("TMDB查询结果{},key:{}", response, keyword);
                return response;
            }
            throw new ServiceException(body);
        }catch (Exception error){
            log.error("TMDB查询异常", error);
            throw new ServiceException("TMDB查询异常");
        }
    }

    /**
     * @Author: hoxise
     * @Description: 获取电影详情 根据tmbdid
     * @Date: 2025/12/22 上午10:41
     */
    public static TMDBMulitSearchResponse searchMovieById(String tmbdId){
        rateLimiter.acquire();
        try{
            String body = HttpUtil.createGet(String.format("https://api.themoviedb.org/3/movie/%s", tmbdId))
                    .setHttpProxy(ProxyHost, ProxyPort)
                    .header("accept", "application/json")
                    .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkMjk5MWNjZjExYTFhZTk2MTAzODc2NzI3NDcyZDE4MSIsIm5iZiI6MTc2NjM2NTQ5NS4xMTYsInN1YiI6IjY5NDg5OTM3MmNkMDhhMjUzOTA3MzJlZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.NEDRgJoal9LTk_1gYUzCupFB4WtlvEALFsNrXNpXj1A")
                    .execute()
                    .body();
            if (body != null){
                TMDBMulitSearchResponse response = JSONObject.parseObject(body, TMDBMulitSearchResponse.class);
                log.info("TMDB查询结果{},movieId:{}", response, tmbdId);
                return response;
            }
            throw new ServiceException(body);
        }catch (Exception error){
            log.error("TMDB查询异常", error);
            throw new ServiceException("TMDB查询异常");
        }
    }

    /**
     * @Author: hoxise
     * @Description: 获取图片流
     * @Date: 2025/12/22 上午10:41
     */
    public static InputStream getUrl(String shortImgUrl){
        String url = TMDB_ImgPrefix + shortImgUrl;
        return HttpUtil.createGet(url)
                .setHttpProxy(ProxyHost, ProxyPort)
                .execute()
                .bodyStream();
    }

}
