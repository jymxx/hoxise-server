package cn.hoxise.module.system.mq.producer;

import cn.hoxise.module.system.mq.message.SmsSendMessage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 短信发送生产者
 *
 * @author hoxise
 * @since 2026/1/30 下午2:26
 */
@Component
@Slf4j
public class SmsSendProducer {

    @Resource
    private RabbitTemplate  rabbitTemplate;

    /**
     * 发送短信消息
     *
     * @param message 短信发送消息
     * @author hoxise
     * @since 2026/1/30 下午2:26
     */
    public void sendSmsMessage(SmsSendMessage message) {
        log.info("发送短信消息：mobile={}", message.getMobile());
        rabbitTemplate.convertAndSend(SmsSendMessage.EXCHANGE, SmsSendMessage.ROUTING_KEY, message);
    }


    /**
     * 异步发送短信消息
     * 通常是后置通知
     *
     * @param message 短信发送消息
     * @author hoxise
     * @since 2026/1/30 下午2:26
     */
    @Async("systemTaskExecutor")
    public void asyncSendSmsMessage(SmsSendMessage message) {
        sendSmsMessage(message);
    }

}
