package cn.hoxise.self.movie.controller.movie.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author hoxise
 * @Description: 影视统计数据
 * @Date 2025-12-22 下午8:01
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

    //日剧
    private Long totalJpTV;
}
