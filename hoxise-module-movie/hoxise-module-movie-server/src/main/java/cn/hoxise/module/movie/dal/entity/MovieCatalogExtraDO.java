package cn.hoxise.module.movie.dal.entity;

import cn.hoxise.common.mybatis.core.dataobject.BaseDO;
import cn.hoxise.module.movie.enums.MovieResourceTypeEnum;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * 影视目录扩展信息表
 *
 * @author hoxise
 * @since 2026/04/06
 */
@Schema(description = "影视目录扩展信息")
@TableName(value = "movie_catalog_extra", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieCatalogExtraDO extends BaseDO {

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
     * 文件id
     */
    private String fileId;

    /**
     * 资源类型
     */
    private MovieResourceTypeEnum resourceType;

    /**
     * 其他云盘存储
     */
    private String otherCloudUrl;

    /**
     * 显示名称
     */
    private String showName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 密钥
     */
    private String secret;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
