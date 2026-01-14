package cn.hoxise.self.movie.service.tmdb;

import lombok.Data;

import java.util.List;

/**
 * TMDB多重查询结果
 *
 * @author hoxise
 * @since 2026/01/14 15:02:15
 */
@Data
public class TMDBMulitSearchResponse {

    /**
     * 当前页码
     */
    private int page;

    /**
     * 搜索结果列表
     */
    private List<TMDBMulitSearchItem> results;

    /**
     * 总页数
     */
    private int totalPages;

    /**
     * 总结果数
     */
    private int totalResults;
}
