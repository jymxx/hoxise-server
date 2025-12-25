package cn.hoxise.common.ai.service;

import cn.hoxise.common.ai.api.OpenAiApi;
import org.springframework.ai.chat.client.ChatClient;
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

    private final ChatClient chatClient;
    @Autowired
    public OpenAiApiImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public ChatClient getChatClient(){
        return chatClient;
    }

    @Override
    public String chat(String userText){
       return chatClient.prompt()
                .user(userText)
                .call()
                .content();
    }


}
