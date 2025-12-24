package cn.hoxise.self.biz.controller.movie.vo;

import cn.hoxise.common.base.utils.date.DateUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author hoxise
 * @Description: 章节信息
 * @Date 2025-12-24 下午1:44
 */
@Data
@Builder
@AllArgsConstructor
public class MovieEpisodesVO {


    private Long id;

    /**
     * 剧集ID
     */
    private Long episodeId;

    /**
     * 目录id
     */
    private Long catalogid;

    /**
     * 播放日期
     */
    @JsonFormat(pattern = DateUtil.FORMAT_YEAR_MONTH_DAY)
    private LocalDateTime airdate;

    /**
     * 原始名称
     */
    private String name;

    /**
     * 中文名称
     */
    private String nameCn;

    /**
     * 持续时间 HH:mm:ss格式
     */
    private String duration;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 集数
     */
    private Integer ep;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 评论数
     */
    private Integer comment;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 磁盘 ？不太清楚
     */
    private Integer disc;

    /**
     * 时长 单位秒
     */
    private Integer durationSeconds;
}
