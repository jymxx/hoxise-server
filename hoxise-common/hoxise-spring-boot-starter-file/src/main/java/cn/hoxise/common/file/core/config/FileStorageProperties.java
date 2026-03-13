package cn.hoxise.common.file.core.config;

import cn.hoxise.common.file.core.pojo.enums.FileStorageTypeEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 文件存储配置属性类
 *
 * @author hoxise
 */
@Data
@ConfigurationProperties(prefix = "file-storage")
public class FileStorageProperties {

    /**
     * 默认存储类型
     */
    private FileStorageTypeEnum defaultType;

    /**
     * MinIO 配置
     */
    private MinioConfig minio = new MinioConfig();

    /**
     * 阿里云OSS配置
     */
    private AliyunOssConfig aliyunOss = new AliyunOssConfig();


    /**
     * 通用配置内部类
     */
    @Data
    public static class ClientProperties {

        /**
         * 存储服务地址
         */
        private String endpoint;

        /**
         * 存储桶名称
         */
        private String bucket;

        /**
         * accessKey
         */
        private String accessKey;

        /**
         * accessSecret
         */
        private String accessSecret;

        /**
         * 序列化前缀，如：配置策略后直接访问的地址；CDN加速服务器
         */
        private String serializerPrefix;
    }

    /**
     * MinIO 配置内部类
     */
    @Data
    public static class MinioConfig extends ClientProperties{

    }

    /**
     * 阿里云OSS配置内部类
     */
    @Data
    public static class AliyunOssConfig extends ClientProperties{

        private String region;

    }

}
