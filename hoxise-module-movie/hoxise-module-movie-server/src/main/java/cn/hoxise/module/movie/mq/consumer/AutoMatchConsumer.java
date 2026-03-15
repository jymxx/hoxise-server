package cn.hoxise.module.movie.mq.consumer;

import cn.hoxise.module.movie.mq.message.AutoMatchMessage;
import cn.hoxise.module.movie.service.MovieManageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 自动匹配消息消费者
 *
 * @author hoxise
 * @since 2026/3/13
 */
@Component
@Slf4j
@RabbitListener(queues = AutoMatchMessage.QUEUE)
public class AutoMatchConsumer {

    @Resource
    private MovieManageService movieManageService;

    @RabbitHandler
    public void onMessage(AutoMatchMessage message) {
        log.info("接收到自动匹配消息：userId={}", message.getLoginId());
        try {
            movieManageService.matchDb(message.getLoginId());
            log.info("用户 {} 自动匹配任务执行成功", message.getLoginId());
        } catch (Exception e) {
            log.error("用户 {} 自动匹配任务执行失败", message.getLoginId(), e);
            throw e;
        }
    }
}
