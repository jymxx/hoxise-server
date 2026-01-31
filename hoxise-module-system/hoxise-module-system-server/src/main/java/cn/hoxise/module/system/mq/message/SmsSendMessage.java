package cn.hoxise.module.system.mq.message;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 短信发送消息
 *
 * @author hoxise
 * @since 2026/1/30 下午2:25
 */
@Data
@AllArgsConstructor
public class SmsSendMessage {

    public static final String QUEUE = "QUEUE_SMS_SEND";

    public static final String EXCHANGE = "EXCHANGE_SMS_SEND";

    public static final String ROUTING_KEY = "ROUTING_KEY_SMS_SEND";

    /**
     * 手机号
     */
    private String mobile;
}
