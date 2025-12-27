package cn.hoxise.common.ai.api;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;

/**
 * @Author hoxise
 * @Description: Api接口
 * @Date 2025-12-24 下午11:31
 */
public interface OpenAiApi {
    
    /**
     * 获取默认ChatClient 暂时不考虑多api
     * @return
     */
    ChatClient getChatClient();

    ChatClient getMemoryChatClient();

    ChatClient getDeepSeekReasonerClient();

    String chat(String userText);
}
