package cn.hoxise.common.ai.config.openai;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * RedisProperties
 *
 * @author hoxise
 * @since 2026/01/14 07:14:58
 */
@Data
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedisProperties {

    /**
     * 连接地址
     */
    private String host;

    /**
     * 连接端口
     */
    private Integer port;

    /**
     * 用户
     */
    private String user;

    /**
     * 连接密码
     */
    private String password;
}
