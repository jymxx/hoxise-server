package cn.hoxise.module.system.mq.config;

import cn.hoxise.module.system.mq.message.SmsSendMessage;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMq
 *
 * @author hoxise
 * @since 2026/1/31 下午12:10
 */
@Configuration
public class SystemRabbitMqConfig {

    @Bean
    public MessageConverter systemMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Direct Exchange 配置类
     */
    public static class DirectExchangeDemoConfiguration {

        // 创建 Queue
        @Bean
        public Queue smsSendQueue() {
            return new Queue(SmsSendMessage.QUEUE, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false); // autoDelete: 是否自动删除
        }

        // 创建 Direct Exchange
        @Bean
        public DirectExchange smsSendExchange() {
            return new DirectExchange(SmsSendMessage.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

        // 创建 Binding
        @Bean
        public Binding smsSendBinding() {
            return BindingBuilder.bind(smsSendQueue()).to(smsSendExchange()).with(SmsSendMessage.ROUTING_KEY);
        }

    }
}
