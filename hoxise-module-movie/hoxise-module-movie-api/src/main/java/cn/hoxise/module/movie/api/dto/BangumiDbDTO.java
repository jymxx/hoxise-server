package cn.hoxise.module.movie.api.dto;

import cn.hoxise.module.movie.enums.bangumi.BangumiSubjectTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * MovieDbBangumiDTO
 *
 * @author hoxise
 * @since 2026/01/14 14:57:04
 */
@Schema(description = "影视DB数据")
@Data
public class BangumiDbDTO {

    private Long id;

    /**
     * 目录id
     */
    private Long catalogid;

    /**
     * bangumi数据库的id
     */
    private Long bangumiId;

    /**
     * 匹配名称
     */
    private String matchingName;

    /**
     * 匹配类型(动漫/电视剧) {@link BangumiSubjectTypeEnum}
     */
    private BangumiSubjectTypeEnum subjectType;

    /**
     * 发布日期
     */
    private LocalDateTime releaseDate;

    /**
     * 发布平台（如：漫画、小说、TV等）
     */
    private String platform;

    /**
     * 封面图片URL
     */
    private String posterUrl;

    /**
     * 简介
     */
    private String summary;

    /**
     * 原始名称
     */
    private String originalName;

    /**
     * 中文名
     */
    private String nameCn;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 评分
     */
    private Double rating;


    /**
     * 元标签列表
     */
    private List<String> metaTags;

    /**
     * 是否为系列 0是 1否
     */
    private Boolean series;

    /**
     * 集数
     */
    private Integer eps;

    /**
     * 卷数
     */
    private Integer volumes;

    /**
     * 总集数
     */
    private Integer totalEpisodes;

    /**
     * 数据库匹配时间
     */
    private LocalDateTime matchingTime;

}