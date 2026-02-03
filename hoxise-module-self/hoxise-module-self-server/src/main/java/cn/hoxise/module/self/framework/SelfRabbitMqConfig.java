package cn.hoxise.module.self.framework;

import cn.hoxise.module.self.mq.message.RwTransMessage;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * RabbitMqConfig
 *
 * @author hoxise
 * @since 2026/02/03 17:33:50
 */
@Configuration
public class SelfRabbitMqConfig {

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Direct Exchange 配置类
     */
    public static class DirectExchangeDemoConfiguration {

        // 创建 Queue
        @Bean
        public Queue smsSendQueue() {
            return new Queue(RwTransMessage.QUEUE, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false,// autoDelete: 是否自动删除
                    // 设置队列参数
                    java.util.Map.of(
                            "x-max-length", 10,          // 设置最大消息数量
                            "x-overflow", "reject-publish" // 溢出策略：拒绝新消息
                    ));
        }

        // 创建 Direct Exchange
        @Bean
        public DirectExchange smsSendExchange() {
            return new DirectExchange(RwTransMessage.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

        // 创建 Binding
        @Bean
        public Binding demo01Binding() {
            return BindingBuilder.bind(smsSendQueue()).to(smsSendExchange()).with(RwTransMessage.ROUTING_KEY);
        }

    }
}
