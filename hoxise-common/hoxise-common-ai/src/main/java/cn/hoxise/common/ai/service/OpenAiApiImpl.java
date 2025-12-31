package cn.hoxise.common.ai.service;

import cn.hoxise.common.ai.api.OpenAiApi;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author hoxise
 * @Description: openai
 * https://springdoc.cn/spring-ai/index.html
 * @Date 2025-12-24 下午11:31
 */
@Service
public class OpenAiApiImpl implements OpenAiApi {

    //DeepSeek推理模型
    private final ChatClient deepSeekReasonerClient;
    //记忆
    private final ChatClient memoryChatClient;
    private final ChatClient chatClient;
    @Autowired
    public OpenAiApiImpl(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory,OpenAiChatModel.Builder chatModelBuilder) {
        this.chatClient = chatClientBuilder
                .build();
        this.memoryChatClient = chatClientBuilder.clone()
                //会话记忆
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
        this.deepSeekReasonerClient = chatClientBuilder.clone()
                //思考模型
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultOptions(ChatOptions.builder().model("deepseek-reasoner").build()).build();
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
    public ChatClient getDeepSeekReasonerClient(){
        return deepSeekReasonerClient;
    }

    @Override
    public String chat(String userText){
       return chatClient.prompt()
                .user(userText)
                .call()
                .content();
    }


}
