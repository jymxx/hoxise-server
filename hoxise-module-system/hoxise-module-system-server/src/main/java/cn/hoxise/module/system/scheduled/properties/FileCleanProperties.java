package cn.hoxise.module.system.scheduled.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件清理配置属性
 *
 * @author hoxise
 * @since 2026/07/08
 */
@Data
@Component
@ConfigurationProperties(prefix = "hoxise.file-storage.file-clean")
public class FileCleanProperties {

    /**
     * 是否启用文件清理
     */
    private boolean enabled;

    /**
     * 过期天数
     */
    private int expireDays;

    /**
     * 清理任务 cron 表达式
     */
    private String cron;
}
