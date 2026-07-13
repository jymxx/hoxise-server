package cn.hoxise.module.movie.controller.movie.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 影视目录扩展信息 - 更新 DTO
 *
 * @author hoxise
 * @since 2026/07/08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "影视目录扩展信息更新请求")
public class MovieCatalogExtraUpdateDTO {

    @Schema(description = "主键 ID")
    @NotNull(message = "id 不能为空")
    private Long id;

    @Schema(description = "显示名称")
    @Size(max = 20, message = "显示名称长度不能超过20")
    private String showName;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "云盘存储地址")
    private String cloudDriveUrl;

}
