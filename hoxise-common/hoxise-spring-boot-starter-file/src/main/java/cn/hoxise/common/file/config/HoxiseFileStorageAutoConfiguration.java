package cn.hoxise.common.file.config;

import cn.hoxise.common.file.core.FileStorageStrategyFactory;
import cn.hoxise.common.file.core.annotations.FilePathSerializer;
import cn.hoxise.common.file.core.service.AliyunOssServiceImpl;
import cn.hoxise.common.file.core.service.FileStorageService;
import cn.hoxise.common.file.core.service.MinioServiceImpl;
import cn.hoxise.common.file.utils.FileStorageUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * 文件存储自动配置
 *
 * @author hoxise
 * @since 2026/1/14 下午3:46
 */
@AutoConfiguration
public class HoxiseFileStorageAutoConfiguration {

    @Bean("aliyunOssServiceImpl")
    @ConditionalOnProperty("fileStorage.aliyunOss.endpoint")
    public FileStorageService aliyunOssServiceImpl() {
        return new AliyunOssServiceImpl();
    }

    @Bean("minioServiceImpl")
    @ConditionalOnProperty("fileStorage.minio.endpoint")
    public FileStorageService minioServiceImpl() {
        return new MinioServiceImpl();
    }

    @Bean
    @ConditionalOnBean(FileStorageService.class)
    public FileStorageStrategyFactory fileStorageStrategyFactory() {
        return new FileStorageStrategyFactory();
    }

    /**
     * 文件路径序列化
     */
    @Bean
    public FilePathSerializer filePathSerializer() {
        return new FilePathSerializer();
    }

    /*
     * 文件工具
     */
    @Bean
    @ConditionalOnProperty("fileStorage.defaultType")
    public FileStorageUtil fileStorageUtil() {
        return new FileStorageUtil();
    }
}
