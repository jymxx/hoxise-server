package cn.hoxise.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author hoxise
 * @Description: 线程池配置
 * @Date 2026-01-13 下午4:45
 */
@Configuration
public class AsyncConfig {

    /**
     * 默认异步任务线程池
     */
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);//核心线程数
        executor.setMaxPoolSize(5);//最大线程数
        executor.setQueueCapacity(100);//队列容量
        executor.setKeepAliveSeconds(60);//线程空闲时间
        executor.setThreadNamePrefix("hoxise-java-async-task-");//线程前缀名
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());//拒绝策略
        executor.setWaitForTasksToCompleteOnShutdown(true);//线程池关闭时等待所有任务完成
        executor.setAwaitTerminationSeconds(60);//任务关闭等待时间
        executor.initialize();
        return executor;
    }

}
