package cn.hoxise.module.movie.dal.entity;

import cn.hoxise.common.mybatis.core.dataobject.BaseDO;
import cn.hoxise.module.movie.pojo.dto.PlayUrlDTO;
import cn.hoxise.module.movie.pojo.dto.ResourceUrlDTO;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.io.Serial;
import java.util.List;
import java.util.Map;

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
     * 播放地址
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private PlayUrlDTO playUrl;

    /**
     * 资源地址列表
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<ResourceUrlDTO> resourceUrl;

    /**
     * 扩展数据（保留字段）
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> extraData;

    /**
     * 密钥
     */
    private String secret;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
