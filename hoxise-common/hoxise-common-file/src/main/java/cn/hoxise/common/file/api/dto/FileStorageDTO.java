package cn.hoxise.common.file.api.dto;

import cn.hoxise.common.file.annotations.FilePathFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * @Author tangxin
 * @Description: 文件存储数据传输对象
 * 通常来说 objectName对前端页面展示是没有意义的 所以页面上直接返回当前存储方式的绝对路径 在字段上添加注解即可实现{@link FilePathFormat}
 * * 暂不支持多个存储系统的情况 考虑后续在注解上添加文件存储枚举的参数
 * @Date 2024-07-16 上午10:06
 */
@Data
@Builder
public class FileStorageDTO {

    @Schema(name = "或称filePath,存储文件路径名称",example = ".../.../objectName"
            ,description = "建议数据库存储该结果值")
    private String objectName;

    @Schema(name = "绝对路径",example = "http://127.0.0.1:9000/minio/bucketName/objectName"
            ,description = "ObjectName使用注解@FilePathFormat自动序列化成该结果;" +
            "如果是Minio私有桶，则建议配置访问策略/或 修改返回给前端预览地址(返回预览地址在华为Obs存储同样适用)")
    private String absoluteUrl;
}
