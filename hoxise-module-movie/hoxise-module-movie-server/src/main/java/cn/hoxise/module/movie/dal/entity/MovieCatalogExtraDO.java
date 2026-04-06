package cn.hoxise.module.movie.dal.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 影视目录扩展信息表
 *
 * @author hoxise
 * @since 2026/04/06
 */
@Schema(description = "影视目录扩展信息")
@TableName("movie_catalog_extra")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieCatalogExtraDO implements Serializable {

    /**
     * 主键 ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关联目录 ID
     */
    private Long catalogId;

    /**
     * 播放地址
     */
    private String playUrl;

    /**
     * 扩展数据
     */
    private String extraData;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
