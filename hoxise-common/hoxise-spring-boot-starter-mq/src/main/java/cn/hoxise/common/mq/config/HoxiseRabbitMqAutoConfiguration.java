package cn.hoxise.common.mq.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * RabbitMq自动配置
 *
 * @author hoxise
 * @since 2026/4/4 下午7:58
 */
@AutoConfiguration
public class HoxiseRabbitMqAutoConfiguration {

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
