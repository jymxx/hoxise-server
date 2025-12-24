package cn.hoxise.self.biz.service.tmdb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author hoxise
 * @Description: TMDB数据接收对象
 * @Date 2025-12-22 上午10:40
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TMDBMulitSearchItem {

    /**
     * 是否为成人内容
     */
    private boolean adult;

    /**
     * 背景图片路径
     */
    private String backdropPath;

    /**
     * 唯一标识符
     */
    private int id;

    /**
     * 媒体类型 (tv/movie)
     */
    private String mediaType;

    /**
     * 原始语言
     */
    private String originalLanguage;

    /**
     * 概述/简介
     */
    private String overview;

    /**
     * 流行度评分
     */
    private double popularity;

    /**
     * 海报图片路径
     */
    private String posterPath;

    /**
     * 类型ID列表
     */
    private List<Integer> genreIds;

    /**
     * 用户评分平均值
     */
    private double voteAverage;

    /**
     * 投票总数
     */
    private int voteCount;

    /**
     * 视频内容标识
     */
    private Boolean video;

    /**
     * 电视剧名称 (仅适用于电视节目)
     */
    private String name;

    /**
     * 电视剧原始名称 (仅适用于电视节目)
     */
    private String originalName;

    /**
     * 电视剧首播日期 (仅适用于电视节目)
     */
    private String firstAirDate;

    /**
     * 电影标题 (仅适用于电影)
     */
    private String title;

    /**
     * 电影原始标题 (仅适用于电影)
     */
    private String originalTitle;

    /**
     * 电影发布日期 (仅适用于电影)
     */
    private String releaseDate;

    /**
     * 原产国 (仅适用于电视节目)
     */
    private List<String> originCountry;
}
