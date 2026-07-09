package cn.hoxise.module.movie.controller.movie.dto;

import cn.hoxise.module.movie.enums.MovieResourceTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 影视目录扩展信息 - 保存 DTO
 *
 * @author hoxise
 * @since 2026/07/08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "影视目录扩展信息保存请求")
public class MovieCatalogExtraSaveDTO {

    @Schema(description = "关联目录 ID")
    @NotNull(message = "catalogId 不能为空")
    private Long catalogId;

    @Schema(description = "资源类型")
    @NotNull(message = "资源类型不能为空")
    private MovieResourceTypeEnum resourceType;

    @Schema(description = "文件 ID")
    private String fileId;

    @Schema(description = "其他云盘存储直链地址")
    private String otherCloudUrl;

    @Schema(description = "显示名称")
    @NotBlank(message = "显示名称不能为空")
    private String showName;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "密钥")
    private String secret;
}
