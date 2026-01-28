package cn.hoxise.common.openai.core;

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
     * 获取指定模型名的聊天客户端
     *
     * @param modelName 模型名
     * @return chatClient
     * @author hoxise
     * @since 2026/01/14 07:13:53
     */
    ChatClient getChatClient(String modelName);

    /**
     * SpringAi RAG模块增强 可将用户查询内容输入到大模型重写
     *
     * @param userText 用户文本
     * @return 重写后的文本
     * @author hoxise
     * @since 2026/01/14 23:54:46
     */
    String ragRewriteQueryTransformer(String userText);

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
