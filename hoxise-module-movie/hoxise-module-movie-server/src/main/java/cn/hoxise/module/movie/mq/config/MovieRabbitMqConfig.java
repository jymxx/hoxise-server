package cn.hoxise.module.movie.mq.config;

import cn.hoxise.module.movie.mq.message.AutoMatchMessage;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 配置类
 *
 * @author hoxise
 * @since 2026/3/13
 */
@Configuration
public class MovieRabbitMqConfig {

    // 创建 Queue
    @Bean
    public Queue autoMatchQueue() {
        return new Queue(AutoMatchMessage.QUEUE,
                true,
                false,
                false);
    }

    // 创建 Direct Exchange
    @Bean
    public DirectExchange autoMatchExchange() {
        return new DirectExchange(AutoMatchMessage.EXCHANGE,
                true,
                false);
    }

    // 创建 Binding
    @Bean
    public Binding autoMatchBinding() {
        return BindingBuilder.bind(autoMatchQueue())
                .to(autoMatchExchange())
                .with(AutoMatchMessage.ROUTING_KEY);
    }

}
