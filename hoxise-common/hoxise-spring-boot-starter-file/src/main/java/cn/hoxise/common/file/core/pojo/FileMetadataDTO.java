package cn.hoxise.common.file.core.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * OSS 文件元数据
 *
 * @author hoxise
 * @since 2026/07/09
 */
@Data
@Builder
public class FileMetadataDTO {

    @Schema(description = "文件大小（字节）")
    private long contentLength;

    @Schema(description = "MIME 类型")
    private String contentType;

    @Schema(description = "MD5 校验值（Base64）")
    private String contentMd5;

    @Schema(description = "内容编码（如 gzip）")
    private String contentEncoding;

    @Schema(description = "内容展示方式（inline / attachment）")
    private String contentDisposition;

    @Schema(description = "最后修改时间")
    private Date lastModified;
}
