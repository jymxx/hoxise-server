package cn.hoxise.module.system.mq.consumer;

import cn.hoxise.module.system.mq.message.SmsSendMessage;
import cn.hoxise.module.system.service.sms.AliyunSmsClient;
import cn.hoxise.module.system.service.sms.SystemSmsLogService;
import cn.hoxise.module.system.service.sms.SystemSmsSendService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 短信发送消费者
 *
 * @author hoxise
 * @since 2026/1/30 下午3:17
 */
@Component
@Slf4j
@RabbitListener(queues = SmsSendMessage.QUEUE)
public class SmsSendConsumer {

    @Resource private SystemSmsSendService systemSmsSendService;

    @RabbitHandler
    public void onMessage(SmsSendMessage message) {
        systemSmsSendService.sendVerifyCodeAliyun(message.getMobile());
    }
}
