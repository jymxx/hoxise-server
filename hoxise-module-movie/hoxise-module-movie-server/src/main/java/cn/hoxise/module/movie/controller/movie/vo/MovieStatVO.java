package cn.hoxise.module.movie.controller.movie.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 影视统计数据
 *
 * @author hoxise
 * @since 2026/01/14 14:55:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieStatVO {

    //总数
    private Long totalCount;

    //动漫
    private Long totalAnime;

    //动漫电影
    private Long totalAnimeMovie;

    //其它
    private Long totalOther;
}
