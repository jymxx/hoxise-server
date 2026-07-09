package cn.hoxise.common.framework.config;

import cn.hoxise.common.framework.core.banner.BannerApplicationRunner;
import cn.hoxise.common.framework.core.jackson.JacksonConfiguration;
import cn.hoxise.common.framework.core.swagger.SwaggerConfig;
import cn.hoxise.common.framework.core.web.CorsFilterConfig;
import cn.hoxise.common.framework.core.web.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;

/**
 * 框架自动配置
 *
 * @author hoxise
 * @since 2026/1/14 下午3:46
 */
@AutoConfiguration
@Import({
        CorsFilterConfig.class, // 跨域配置
        SwaggerConfig.class, // swagger配置
        JacksonConfiguration.class,// jackson序列化配置
})
public class HoxiseFrameworkAutoConfiguration {

    /**
     * 全局异常处理
     */
    @Bean
    @Order(99) //优先级降低 如果有其它拦截器则让其它拦截器先处理
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    /**
     * 启动时打印banner
     */
    @Bean
    public BannerApplicationRunner bannerApplicationRunner() {
        return new BannerApplicationRunner();
    }


}
