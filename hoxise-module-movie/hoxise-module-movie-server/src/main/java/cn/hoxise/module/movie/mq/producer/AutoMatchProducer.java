package cn.hoxise.module.movie.mq.producer;

import cn.hoxise.module.movie.mq.message.AutoMatchMessage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 自动匹配消息生产者
 *
 * @author hoxise
 * @since 2026/3/13
 */
@Component
@Slf4j
public class AutoMatchProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送自动匹配消息
     *
     * @param message 自动匹配消息
     * @author hoxise
     * @since 2026/3/13
     */
    public void sendAutoMatchMessage(AutoMatchMessage message) {
        log.info("发送自动匹配消息：userId={}", message.getLoginId());
        rabbitTemplate.convertAndSend(AutoMatchMessage.EXCHANGE, AutoMatchMessage.ROUTING_KEY, message);
    }

}
