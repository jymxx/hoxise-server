package cn.hoxise.module.movie.controller.movie.vo;

import cn.hoxise.common.base.utils.date.DateUtil;
import cn.hoxise.common.file.core.annotations.FilePathFormat;
import cn.hoxise.module.movie.enums.bangumi.BangumiSubjectTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 影视目录返回类
 *
 * @author hoxise
 * @since 2026/01/14 14:55:21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieSimpleVO {

    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 文件大小
     */
    private Double totalSize;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DateUtil.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = DateUtil.TIME_ZONE_DEFAULT)
    private LocalDateTime createTime;

    /**
     * bangumi id
     */
    private Long bangumiId;

    /**
     * 原始名称
     */
    private String originName;

    /**
     * 海报地址
     */
    @FilePathFormat
    private String posterUrl;

    /**
     * 平台
     */
    private String platform;

    /**
     * 评分
     */
    private Double rating;

    /**
     * 上映年份
     */
    private Integer releaseYear;

    /**
     * 集数
     */
    private Integer eps;

    /**
     * 元标签
     */
    private List<String> metaTags;

    /**
     * 是否收藏(与登录用户绑定)
     */
    private boolean isFavorite;

}
