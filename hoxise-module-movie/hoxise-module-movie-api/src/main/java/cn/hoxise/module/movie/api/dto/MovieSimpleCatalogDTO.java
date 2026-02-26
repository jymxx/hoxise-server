package cn.hoxise.module.movie.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 简单目录数据
 *
 * @author hoxise
 * @since 2026/2/25 上午8:12
 */
@Schema(description = "影视目录数据")
@Data
public class MovieSimpleCatalogDTO {

    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 原始名称
     */
    private String originName;
}
