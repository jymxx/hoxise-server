package cn.hoxise.module.self.mq.consumer;

import cn.hoxise.module.self.mq.message.RwTransMessage;
import cn.hoxise.module.self.service.RwTransService;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 翻译
 *
 * @author hoxise
 * @since 2026/2/3 下午8:29
 */
@Component
@RabbitListener(queues = RwTransMessage.QUEUE)
public class RwTransConsumer {

    @Resource
    private RwTransService rwTransService;

    @RabbitHandler
    public void onMessage(RwTransMessage message) {
        rwTransService.handleTrans(message);
    }
}
