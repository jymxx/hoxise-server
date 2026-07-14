package cn.hoxise.module.system.controller.file.dto;

import cn.hoxise.module.system.enums.FileOssDirEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 预签名上传请求 DTO
 *
 * @author hoxise
 * @since 2026/07/14
 */
@Data
public class PresignedUploadReqDTO {

    @Schema(description = "原始文件名（含扩展名）", example = "poster.jpg")
    @NotBlank(message = "文件名不能为空")
    private String fileName;

    @Schema(description = "业务类型", example = "MOVIE_RESOURCE")
    @NotNull(message = "业务类型不能为空")
    private FileOssDirEnum bizType;
}
