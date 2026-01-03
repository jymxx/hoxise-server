package cn.hoxise.common.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author hoxise
 * @Description:redis
 * @Date 2025-12-26 上午7:24
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
