package cn.hoxise.common.ai.core;

import org.springframework.ai.chat.client.ChatClient;

/**
 * OpenAiApi规范接口
 *
 * @author hoxise
 * @since 2026/01/14 07:12:50
 */
public interface OpenAiApi {

    /**
     * 获取聊天客户端
     *
     * @return chatClient
     * @author hoxise
     * @since 2026/01/14 07:13:06
     */
    ChatClient getChatClient();

    /**
     * 获取带上下文记忆的聊天客户端
     *
     * @return chatClient
     * @author hoxise
     * @since 2026/01/14 07:13:33
     */
    ChatClient getMemoryChatClient();

    /**
     * 聊天
     *
     * @param userText 用户文本
     * @return 返回
     * @author hoxise
     * @since 2026/01/14 07:21:41
     */
    String chat(String userText);
}
