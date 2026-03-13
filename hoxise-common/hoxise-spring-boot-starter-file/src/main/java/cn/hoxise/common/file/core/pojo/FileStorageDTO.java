package cn.hoxise.common.file.core.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 文件存储结果DTO
 *
 * @author hoxise
 * @since 2026/01/14 06:43:23
 */
@Data
@Builder
public class FileStorageDTO {

    @Schema(name = "文件存储路径",example = ".../.../objectName",description = "对象名称")
    private String objectName;

    @Schema(name = "绝对路径",example = "http://127.0.0.1:9000/minio/bucketName/objectName"
            ,description = "如果是私有桶，建议配置访问策略/或修改返回给前端预览地址")
    private String absoluteUrl;
}
