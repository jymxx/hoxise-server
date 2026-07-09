package cn.hoxise.module.system.controller.file.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 预签名上传响应 VO
 *
 * @author hoxise
 * @since 2026/07/09
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PresignedUploadVO {

    @Schema(description = "预签名上传 URL（前端直传 OSS）")
    private String uploadUrl;

    @Schema(description = "文件 ID（上传完成后调用 bindFile 绑定）")
    private String fileId;
}
