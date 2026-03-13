package cn.hoxise.module.ai.api;

import cn.hoxise.module.ai.framework.core.OpenAiClient;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hoxise
 * @since 2026/2/3 下午3:11
 */
@RestController
public class ChatApiImpl implements ChatApi {

    @Resource(name = "deepSeekApiImpl")
    private OpenAiClient openAiClient;

    @Override
    public String chat(String systemText, String userText) {
        return openAiClient.getChatClient().prompt()
                .system(systemText)
                .user(userText)
                .call().content();
    }
}
