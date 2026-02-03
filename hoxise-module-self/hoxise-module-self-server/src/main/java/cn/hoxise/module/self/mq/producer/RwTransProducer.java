package cn.hoxise.module.self.mq.producer;

import cn.hoxise.module.self.mq.message.RwTransMessage;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


/**
 * 翻译消息
 *
 * @author hoxise
 * @since 2026/2/3 下午4:22
 */
@Component
public class RwTransProducer {


    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendTransMessage(RwTransMessage message){
        rabbitTemplate.convertAndSend(RwTransMessage.EXCHANGE, RwTransMessage.ROUTING_KEY, message);
    }

    @Async
    public void asyncSendTransMessage(RwTransMessage message){
        sendTransMessage(message);
    }

}
