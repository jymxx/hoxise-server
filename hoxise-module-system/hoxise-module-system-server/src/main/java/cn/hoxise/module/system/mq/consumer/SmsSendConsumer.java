package cn.hoxise.module.system.mq.consumer;

import cn.hoxise.module.system.mq.message.SmsSendMessage;
import cn.hoxise.module.system.service.sms.AliyunSmsClient;
import cn.hoxise.module.system.service.sms.SystemSmsLogService;
import com.aliyun.dypnsapi20170525.models.SendSmsVerifyCodeResponseBody;
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

    @Resource private SystemSmsLogService systemSmsLogService;

    @RabbitHandler
    public void onMessage(SmsSendMessage message) {
        SendSmsVerifyCodeResponseBody body  = AliyunSmsClient.sendVerifyCodeAliyun(message.getMobile());
        //保存日志
        if (body.getSuccess()){
            String code = body.getModel().getRequestId();
            systemSmsLogService.saveSendLog(message.getMobile(),code,"验证码(阿里云)");
        }else{
            log.error("短信发送失败:"+body.getMessage());
        }
    }
}
