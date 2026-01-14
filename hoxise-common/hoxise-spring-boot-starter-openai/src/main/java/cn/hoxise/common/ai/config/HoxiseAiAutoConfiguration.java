package cn.hoxise.common.ai.config;


import cn.hoxise.common.ai.core.DeepSeekApiImpl;
import cn.hoxise.common.ai.core.OpenAiApi;
import cn.hoxise.common.ai.core.OpenAiApiImpl;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * HoxiseAiAutoConfiguration
 *
 * @author hoxise
 * @since 2026/01/14 07:14:40
 */
@AutoConfiguration
public class HoxiseAiAutoConfiguration {

    @Primary
    @Bean("openAiApi")
    @ConditionalOnProperty("spring.ai.openai.base-url")
    public OpenAiApi openAiApi(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        return new OpenAiApiImpl(chatClientBuilder, chatMemory);
    }

    @Bean("deepSeekApiImpl")
    @ConditionalOnProperty("spring.ai.deepseek.base-url")
    public OpenAiApi deepSeekApiImpl(DeepSeekChatModel chatModel, ChatMemory chatMemory, ChatClient.Builder chatClientBuilder) {
        return new DeepSeekApiImpl(chatModel, chatMemory,chatClientBuilder);
    }

}
