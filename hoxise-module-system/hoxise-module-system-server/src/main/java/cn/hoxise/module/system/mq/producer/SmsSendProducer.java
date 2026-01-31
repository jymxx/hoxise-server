package cn.hoxise.module.system.mq.producer;

import cn.hoxise.module.system.mq.message.SmsSendMessage;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
        rabbitTemplate.convertAndSend(SmsSendMessage.EXCHANGE, SmsSendMessage.ROUTING_KEY, message);
    }


    @Async
    public void asyncSendSmsMessage(SmsSendMessage message) {
        sendSmsMessage(message);
    }

}
