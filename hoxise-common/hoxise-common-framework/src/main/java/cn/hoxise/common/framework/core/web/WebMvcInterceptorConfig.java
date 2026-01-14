package cn.hoxise.common.framework.core.web;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;

/**
 * Web拦截器配置
 *
 * @author hoxise
 * @since 2026/01/14 06:17:17
 */
public class WebMvcInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .maxAge(1800);
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        //MVC异步线程池 比如流式响应
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("hoxise-webmvc-async-");
        executor.initialize();
        configurer.setTaskExecutor(executor);
        configurer.setDefaultTimeout(300000); // 5分钟
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
//        registry.addInterceptor(new SaInterceptor(handle -> {
//                    SaRouter.match("/**")
//                            .notMatch("/system/auth/login")
//                            // 下边的是knife4j使用的
//                            .notMatch("/doc.html")
//                            .notMatch("/webjars/**")
//                            .notMatch("/v3/api-docs/**")
////                            .check(r -> StpUtil.checkLogin())
//                    ;
//                }))
//                .addPathPatterns("/**");
//    }

}