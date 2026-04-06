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
 * 动漫收藏表
 *
 * @author hoxise
 * @since 2026/04/06
 */
@Schema(description = "动漫收藏")
@TableName("movie_favorite")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieFavoriteDO implements Serializable {

    /**
     * 主键 ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户 ID
     */
    private Long userId;

    /**
     * 收藏的目录 ID
     */
    private Long catalogId;

    /**
     * 收藏时间
     */
    private LocalDateTime createTime;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
