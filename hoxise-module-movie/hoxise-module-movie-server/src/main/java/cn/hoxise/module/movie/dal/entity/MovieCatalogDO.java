package cn.hoxise.module.movie.dal.entity;

import cn.hoxise.module.movie.enums.movie.MovieStatusEnum;
import cn.hoxise.module.movie.enums.movie.MovieTypeEnum;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 影视目录
 *
 * @author hoxise
 * @since 2026/01/14 14:56:32
 */
@Schema(description = "影视目录")
@TableName(value ="movie_catalog")
@Data
@Builder
public class MovieCatalogDO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 路径
     */
    private String path;

    /**
     * 名称
     */
    private String name;

    /**
     * 文件大小
     */
    private Double totalSize;

    /**
     * 目录名称
     */
    private MovieTypeEnum directory;

    /**
     * 用户id 属于哪个用户的数据
     */
    private Long userid;

    /**
     * 关联bangumi数据id
     */
    private Long bangumiId;

    /**
     * 数据创建时间
     */
    private LocalDateTime createTime;

    /**
     * 匹配时间
     */
    private LocalDateTime matchingTime;

    /**
     * 存在状态 0正常 1过期
     */
    @TableLogic
    private MovieStatusEnum status;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}