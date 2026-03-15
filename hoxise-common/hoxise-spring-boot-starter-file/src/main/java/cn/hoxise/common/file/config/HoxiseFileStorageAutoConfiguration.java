package cn.hoxise.common.file.config;

import cn.hoxise.common.file.core.client.FileStorageClientFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 文件存储自动配置
 *
 * @author hoxise
 * @since 2026/1/14 下午 3:46
 */
@AutoConfiguration
public class HoxiseFileStorageAutoConfiguration {

    @Bean
    public FileStorageClientFactory fileStorageStrategyFactory() {
        return new FileStorageClientFactory();
    }

}
