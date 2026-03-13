package cn.hoxise.common.file.config;

import cn.hoxise.common.file.core.client.FileStorageClientFactory;
import cn.hoxise.common.file.core.annotations.FilePathSerializer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 文件存储自动配置
 *
 * @author hoxise
 * @since 2026/1/14 下午3:46
 */
@AutoConfiguration
public class HoxiseFileStorageAutoConfiguration {

    @Bean
    public FileStorageClientFactory fileStorageStrategyFactory() {
        return new FileStorageClientFactory();
    }

    /**
     * 文件路径序列化
     */
    @Bean
    public FilePathSerializer filePathSerializer() {
        return new FilePathSerializer();
    }

}
