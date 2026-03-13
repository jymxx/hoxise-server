package cn.hoxise.module.movie.controller.movie.vo;

import cn.hoxise.common.file.core.annotations.FilePathFormat;
import cn.hoxise.module.movie.enums.bangumi.BangumiSubjectTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
     * Bangumi id
     */
    private Long subjectId;

    /**
     * bangumi id
     */
    private Long bangumiId;

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
     * 名称
     */
    private String name;

    /**
     * 评分
     */
    private Double rating;

    /**
     * 上映年份
     */
    private Integer releaseYear;

    /**
     * 类型
     */
    private BangumiSubjectTypeEnum subjectType;

    /**
     * 元标签
     */
    private List<String> metaTags;

}
