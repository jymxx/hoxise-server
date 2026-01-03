package cn.hoxise.self.movie.dal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Bangumi剧集表
 * @TableName movie_db_bangumi_episode
 */
@TableName(value ="movie_db_bangumi_episode")
@Data
@Builder
@AllArgsConstructor
public class MovieDbBangumiEpisodeDO implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}