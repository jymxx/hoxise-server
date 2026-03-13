package cn.hoxise.module.ai.tool.method;

import cn.hoxise.module.movie.api.MovieCatalogApi;
import cn.hoxise.module.movie.api.dto.MovieSimpleCatalogRespDTO;
import jakarta.annotation.Resource;
import org.springframework.ai.tool.annotation.Tool;

import java.util.List;

/**
 * 影视方法实现类
 *
 * @author hoxise
 * @since 2026/2/25 上午7:55
 */
public class MovieServiceImpl implements MovieService{

    @Resource
    private MovieCatalogApi movieCatalogApi;

    @Tool(name = "movie_get_user_movie")
    public void getUserMovie(Long userid){
        List<MovieSimpleCatalogRespDTO> checkedData = movieCatalogApi.listSimpleCatalog(userid).getCheckedData();

    }
}
