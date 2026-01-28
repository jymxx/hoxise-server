package cn.hoxise.common.framework.config;

import cn.hoxise.common.framework.core.banner.BannerApplicationRunner;
import cn.hoxise.common.framework.core.swagger.SwaggerConfig;
import cn.hoxise.common.framework.core.web.GlobalExceptionHandler;
import cn.hoxise.common.framework.core.web.WebMvcInterceptorConfig;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 框架自动配置
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
     * web拦截器配置
     */
    @Bean
    public WebMvcInterceptorConfig webMvcInterceptorConfig() {
        return new WebMvcInterceptorConfig();
    }

    /**
     * Swagger配置
     */
    @Bean
    public SwaggerConfig swaggerConfig() {
        return new SwaggerConfig();
    }

    /**
     * 启动时打印banner
     */
    @Bean
    public BannerApplicationRunner bannerApplicationRunner() {
        return new BannerApplicationRunner();
    }
}
