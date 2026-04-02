package cn.hoxise.module.movie.controller.movie.dto;

import cn.hoxise.common.base.pojo.PageParam;
import cn.hoxise.module.movie.enums.movie.MovieTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 简单查询结果DTO
 *
 * @author hoxise
 * @since 2026/01/14 14:54:33
 */
@Data
public class MovieSimpleQueryDTO extends PageParam {

    @Schema(description = "目录类型", example = "anime/animeMovie")
    private String directory;

    @Schema(description = "搜索名称关键字", example = "Clannad")
    private String keyword;

    @Schema(description = "是否过滤未匹配", example = "true")
    private Boolean notMatched;

    @Schema(description = "用户ID 从请求路径读取 无需参数传递", example = "0")
    private Long userid;
}
