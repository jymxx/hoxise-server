package cn.hoxise.module.movie.controller.movie.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * excel上传DTO
 *
 * @author hoxise
 * @since 2026/3/6 下午11:57
 */
@Data
@Schema(description = "影视目录 Excel 上传 DTO")
public class MovieExcelUploadDTO {

    @Schema(description = "上传的 Excel 文件", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Excel 文件不能为空")
    private MultipartFile file;

    @Schema(description = "目录类型", example = "anime", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "目录类型不能为空")
    private String directoryType;

    @Schema(description = "是否删除缺失的目录", example = "true")
    private Boolean deleteMissing;
}
