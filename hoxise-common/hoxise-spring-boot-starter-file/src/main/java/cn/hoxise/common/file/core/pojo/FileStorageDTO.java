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

    @Schema(name = "或称filePath,存储文件路径名称",example = ".../.../objectName"
            ,description = "建议数据库存储该结果值,方便迁移数据")
    private String objectName;

    @Schema(name = "绝对路径",example = "http://127.0.0.1:9000/minio/bucketName/objectName"
            ,description = "ObjectName使用注解@FilePathFormat自动序列化成该结果;" +
            "如果是私有桶，建议配置访问策略/或 修改返回给前端预览地址")
    private String absoluteUrl;
}
