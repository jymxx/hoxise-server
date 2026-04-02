//package cn.hoxise.server.web;
//
//import cn.dev33.satoken.interceptor.SaInterceptor;
//import cn.dev33.satoken.router.SaRouter;
//import cn.dev33.satoken.stp.StpUtil;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * @author hoxise
// */
//@Component
//public class WebConfigurer implements WebMvcConfigurer {
//
//    /**
//     * 配置异步线程池
//     * webmvc架构 在使用webflux时需要配置异步线程池 如AI的react返回
//     *
//     * @param configurer 配置
//     */
//    @Override
//    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.setCorePoolSize(5);              // 核心线程数
//        taskExecutor.setMaxPoolSize(10);               // 最大线程数
//        taskExecutor.setQueueCapacity(100);            // 队列容量
//        taskExecutor.setThreadNamePrefix("async-web-task-"); // 线程名前缀
//        taskExecutor.initialize();
//        configurer.setTaskExecutor(taskExecutor);
//    }
//
//}
