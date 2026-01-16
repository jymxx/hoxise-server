package cn.hoxise.common.framework.config;

import cn.hoxise.common.framework.core.scheduling.ThreadPoolConfig;
import cn.hoxise.common.framework.core.swagger.SwaggerConfig;
import cn.hoxise.common.framework.core.web.GlobalExceptionHandler;
import cn.hoxise.common.framework.core.web.WebMvcInterceptorConfig;
import cn.hoxise.common.framework.utils.RedisUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 文件存储自动配置
 *
 * @author hoxise
 * @since 2026/1/14 下午3:46
 */
@AutoConfiguration
public class HoxiseFrameworkAutoConfiguration {

    /**
     * 全局异常处理
     */
    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    /**
     * 拦截器配置
     */
    @Bean
    public WebMvcInterceptorConfig webMvcInterceptorConfig() {
        return new WebMvcInterceptorConfig();
    }

    /**
     * 线程池配置
     */
    @Bean
    public ThreadPoolConfig threadPoolConfig() {
        return new ThreadPoolConfig();
    }

    /**
     * Swagger配置
     */
    @Bean
    public SwaggerConfig swaggerConfig() {
        return new SwaggerConfig();
    }

    @Bean
    public RedisUtil redisUtil() {
        return new RedisUtil();
    }


}
