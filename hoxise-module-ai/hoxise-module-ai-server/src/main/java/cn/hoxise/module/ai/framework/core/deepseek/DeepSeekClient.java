package cn.hoxise.module.ai.framework.core.deepseek;

import cn.hoxise.module.ai.framework.core.OpenAiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.preretrieval.query.transformation.QueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * DeepSeekApiImpl
 *
 * @author hoxise
 * @since 2026/01/14 07:12:21
 */
@Component
@RequiredArgsConstructor
public class DeepSeekClient implements OpenAiClient {

    private final DeepSeekChatModel chatModel;
    private final ChatMemory chatMemory;
    private final ChatClient.Builder chatClientBuilder;

    //思考模型
    public static final String MODEL_REASONER = "deepseek-reasoner";
    //聊天模型
    public static final String MODEL_CHAT = "deepseek-chat";

    @Override
    public ChatClient getChatClient() {
        return ChatClient.builder(chatModel).build();
    }

    @Override
    public ChatClient getMemoryChatClient() {
        return ChatClient.builder(chatModel).defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()).build();
    }

    @Override
    public ChatClient getChatClient(String modelName) {
        return chatClientBuilder.defaultOptions(OpenAiChatOptions.builder().model(modelName).build()).build();
    }

    @Override
    public String ragRewriteQueryTransformer(String userText) {
        Query query = new Query(userText);
        QueryTransformer queryTransformer = RewriteQueryTransformer.builder()
                //.promptTemplate("") 使用promptTemplate提示词模板根据业务需求复写用户输入内容
                //用聊天模型 不然太慢了
                .chatClientBuilder(chatClientBuilder.defaultOptions(OpenAiChatOptions.builder().model(MODEL_CHAT).build()))
                .build();
        Query transformedQuery = queryTransformer.transform(query);
        return transformedQuery.text();
    }

    @Override
    public String chat(String userText) {
        return getChatClient().prompt()
                .user(userText)
                .call()
                .content();
    }
}
