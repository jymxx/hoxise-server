package cn.hoxise.server.web;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author hoxise
 */
@Component
public class WebConfigurer implements WebMvcConfigurer {

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
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);              // 核心线程数
        taskExecutor.setMaxPoolSize(10);               // 最大线程数
        taskExecutor.setQueueCapacity(100);            // 队列容量
        taskExecutor.setThreadNamePrefix("async-web-task-"); // 线程名前缀
        taskExecutor.initialize();
        configurer.setTaskExecutor(taskExecutor);
    }

}
