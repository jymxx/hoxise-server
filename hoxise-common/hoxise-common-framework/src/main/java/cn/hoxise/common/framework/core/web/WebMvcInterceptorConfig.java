package cn.hoxise.common.framework.core.web;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Web拦截器配置
 *
 * @author hoxise
 * @since 2026/01/14 06:17:17
 */
public class WebMvcInterceptorConfig implements WebMvcConfigurer {

    /**
     * 跨域过滤器
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterBean() {
        // 创建 CorsConfiguration 对象
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*"); // 设置访问源地址
        config.addAllowedHeader("*"); // 设置访问源请求头
        config.addAllowedMethod("*"); // 设置访问源请求方法
        // 创建 UrlBasedCorsConfigurationSource 对象
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 对接口配置跨域设置
        return new FilterRegistrationBean<>(new CorsFilter(source));
    }

//    @Override
//    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
//        //MVC+webflux 异步支持 比如流式响应
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(3);
//        executor.setMaxPoolSize(10);
//        executor.setQueueCapacity(100);
//        executor.setKeepAliveSeconds(60);
//        executor.setThreadNamePrefix("hoxise-webmvc-async-");
//        executor.initialize();
//        configurer.setTaskExecutor(executor);
//        configurer.setDefaultTimeout(300000); // 5分钟
//    }
}