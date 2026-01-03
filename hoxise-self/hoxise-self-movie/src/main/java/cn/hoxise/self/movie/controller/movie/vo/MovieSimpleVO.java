package cn.hoxise.self.movie.controller.movie.vo;

import cn.hoxise.common.file.annotations.FilePathFormat;
import cn.hoxise.self.movie.pojo.enums.bangumi.BangumiSubjectTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author hoxise
 * @Description: 影视目录返回类
 * @Date 2025-12-22 下午3:41
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
