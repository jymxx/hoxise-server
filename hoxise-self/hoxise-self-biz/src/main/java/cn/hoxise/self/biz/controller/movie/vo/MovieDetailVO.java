package cn.hoxise.self.biz.controller.movie.vo;

import cn.hoxise.common.base.framework.StringListTypeHandler;
import cn.hoxise.common.base.utils.date.DateUtil;
import cn.hoxise.common.file.annotations.ImgFilePathFormat;
import cn.hoxise.self.biz.dal.entity.MovieDbBangumiInfoboxDO;
import cn.hoxise.self.biz.pojo.enums.BangumiSubjectTypeEnum;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author hoxise
 * @Description: 影视详情
 * @Date 2025-12-23 下午3:12
 */
@Data
@Builder
@AllArgsConstructor
public class MovieDetailVO {

    private Long id;

    private String catalogid;

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
    @JsonFormat(pattern = DateUtil.FORMAT_YEAR_MONTH_DAY)
    private LocalDateTime releaseDate;

    /**
     * 发布平台（如：漫画、小说、TV等）
     */
    private String platform;

    /**
     * 封面图片URL
     */
    @ImgFilePathFormat
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
    @TableField(value = "tags",typeHandler = StringListTypeHandler.class)
    private List<String> tags;

    /**
     * 评分
     */
    private Double rating;


    /**
     * 元标签列表
     */
    @TableField(value = "meta_tags",typeHandler = StringListTypeHandler.class)
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
     *  infobox
     */
    private List<MovieDbBangumiInfoboxDO> infobox;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
