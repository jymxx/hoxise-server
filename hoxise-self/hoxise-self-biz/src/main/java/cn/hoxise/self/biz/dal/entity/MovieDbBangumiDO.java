package cn.hoxise.self.biz.dal.entity;

import cn.hoxise.common.base.framework.StringListTypeHandler;
import cn.hoxise.self.biz.pojo.enums.BangumiSubjectTypeEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 影视数据 匹配Bangumi API
 * @TableName movie_db_bangumi
 */
@Schema(description = "影视数据 匹配Bangumi API")
@TableName(value ="movie_db_bangumi")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDbBangumiDO implements Serializable {

    @TableId(type = IdType.AUTO)
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
    @TableField(typeHandler = StringListTypeHandler.class)
    private List<String> tags;

    /**
     * 评分
     */
    private Double rating;


    /**
     * 元标签列表
     */
    @TableField(typeHandler = StringListTypeHandler.class)
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

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}