package cn.hoxise.module.ai.framework.core.openai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Component;

/**
 * OpenAi默认实现
 * 文档https://springdoc.cn/spring-ai/index.html
 *
 * @author hoxise
 * @since 2026/01/14 07:12:31
 */
@Component
public class OpenAiClient implements cn.hoxise.module.ai.framework.core.OpenAiClient {

    //记忆
    private final ChatClient memoryChatClient;
    private final ChatClient chatClient;
    public OpenAiClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        this.chatClient = chatClientBuilder
                .build();
        this.memoryChatClient = chatClientBuilder.clone()
                //会话记忆
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }


    @Override
    public ChatClient getChatClient(){
        return chatClient;
    }

    @Override
    public ChatClient getMemoryChatClient(){
        return memoryChatClient;
    }

    @Override
    public ChatClient getChatClient(String modelName) {
        return null;
    }

    @Override
    public String ragRewriteQueryTransformer(String userText) {
        return "";
    }

    @Override
    public String chat(String userText){
       return chatClient.prompt()
                .user(userText)
                .call()
                .content();
    }


}
