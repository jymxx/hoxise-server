package cn.hoxise.module.movie.framework;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 *
 * @author hoxise
 * @since 2026/1/28 下午5:16
 */
@Component
public class MovieThreadPoolConfiguration {

    /**
     * 默认异步任务执行器
     *
     * @return 线程池执行器
     */
    @Bean("taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);//核心线程数
        executor.setMaxPoolSize(10);//最大线程数
        executor.setQueueCapacity(100);//队列容量
        executor.setKeepAliveSeconds(60);//线程空闲时间
        executor.setThreadNamePrefix("hoxise-module-system-async-");//线程前缀名
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());//拒绝策略
        executor.setWaitForTasksToCompleteOnShutdown(true);//线程池关闭时等待所有任务完成
        executor.setAwaitTerminationSeconds(60);//任务关闭等待时间
        executor.initialize();
        return executor;
    }
}
