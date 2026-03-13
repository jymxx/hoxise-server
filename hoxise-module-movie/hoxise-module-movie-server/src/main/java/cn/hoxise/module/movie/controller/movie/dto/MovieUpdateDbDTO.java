package cn.hoxise.module.movie.controller.movie.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 更新DB
 *
 * @author hoxise
 * @since 2026/3/6 下午1:36
 */
@Data
public class MovieUpdateDbDTO {

    @Schema(description = "目录ID", example = "1")
    @NotNull
    private Long catalogId;

    @Schema(description = "BangumiID", example = "1")
    @NotNull
    private Long bangumiId;

    @Schema(description = "是否强制更新", example = "true")
    private Boolean forceUpdate;
}
