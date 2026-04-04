package cn.hoxise.module.movie.controller.movie.dto;

import cn.hoxise.common.base.pojo.PageParam;
import cn.hoxise.module.movie.enums.movie.MovieTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 简单查询结果DTO
 *
 * @author hoxise
 * @since 2026/01/14 14:54:33
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieSimpleQueryDTO extends PageParam {

    /**
     * 目录类型
     * {@link MovieTypeEnum}
     */
    @Schema(description = "目录类型", example = "anime/animeMovie")
    private String directory;

    @Schema(description = "搜索名称关键字", example = "Clannad")
    private String keyword;

    @Schema(description = "是否过滤未匹配", example = "true", defaultValue = "false")
    private Boolean notMatched = false;

    @Schema(description = "排序字段", example = "id")
    private String orderBy = "id";

    @Schema(description = "是否升序", example = "true", defaultValue = "true")
    private Boolean isAsc = true;

    @Schema(description = "用户ID 从请求路径读取 无需参数传递", example = "0")
    private Long userid;
}
