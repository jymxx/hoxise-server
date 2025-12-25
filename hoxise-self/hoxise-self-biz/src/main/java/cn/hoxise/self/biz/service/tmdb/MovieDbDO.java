package cn.hoxise.self.biz.service.tmdb;

import cn.hoxise.common.base.framework.mybatis.typehandler.StringListTypeHandler;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 
 * @TableName movie_db
 */
@Schema(description = "影视数据 匹配")
@TableName(value ="movie_db")
@Data
@Builder
public class MovieDbDO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private Long catalogid;

    /**
     * tmdb数据库的id
     */
    private Long tmdbId;

    /**
     * 匹配类型(动漫/电视剧)
     */
    private String matchingType;

    /**
     * 匹配名称
     */
    private String matchingName;

    /**
     * 原始名称
     */
    private String originalName;

    /**
     * 别名
     */
    private List<String> anotherName;

    /**
     * 海报地址
     */
    private String posterUrl;

    /**
     * 背景图片地址
     */
    private String backdropUrl;

    /**
     * 简介
     */
    private String overview;

    /**
     * 评分
     */
    private Double voteAverage;

    /**
     * 上映日期
     */
    private LocalDateTime releaseDate;

    /**
     * 地区
     */
    private String originCountry;

    /**
     * 演员列表
     */
    @TableField(typeHandler = StringListTypeHandler.class)
    private List<String> actors;

    /**
     * 集数(电视剧/动漫)
     */
    private Integer episodes;

    /**
     * 时长(电影)
     */
    private String duration;

    /**
     * 标签集合
     */
    @TableField(typeHandler = StringListTypeHandler.class)
    private List<String> label;

    /**
     * 数据库匹配时间
     */
    private LocalDateTime matchingTime;

    /**
     * ai总结
     */
    private String aiIntroduction;

    /**
     * ai总结标签
     */
    @TableField(typeHandler = StringListTypeHandler.class)
    private List<String> aiLabel;

    /**
     * ai总结时间
     */
    private LocalDateTime aiTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}