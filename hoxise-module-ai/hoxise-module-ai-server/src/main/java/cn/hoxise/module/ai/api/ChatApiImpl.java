package cn.hoxise.module.ai.api;

import cn.hoxise.common.openai.core.OpenAiApi;
import cn.hoxise.module.ai.api.ChatApi;
import jakarta.annotation.Resource;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hoxise
 * @since 2026/2/3 下午3:11
 */
@RestController
public class ChatApiImpl implements ChatApi {

    @Resource(name = "deepSeekApiImpl")
    private OpenAiApi openAiApi;

    @Override
    public String chat(String systemText, String userText) {
        return openAiApi.getChatClient().prompt()
                .system(systemText)
                .user(userText)
                .call().content();
    }
}
