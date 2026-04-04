package cn.hoxise.module.movie.controller.movie.dto;

import cn.hoxise.common.base.pojo.PageParam;
import cn.hoxise.module.movie.enums.movie.MovieTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 查询影视库DTO
 *
 * @author hoxise
 * @since 2026/4/4 下午6:07
 */
@Data
public class MovieLibraryQueryDTO extends PageParam {

    /**
     * 目录类型
     * {@link MovieTypeEnum}
     */
    @Schema(description = "目录类型", example = "anime/animeMovie")
    private String directory;

    @Schema(description = "用户ID 从请求路径读取 无需参数传递", example = "0")
    private Long userid;
}
