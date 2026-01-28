package cn.hoxise.module.ai.service.movie;

import reactor.core.publisher.Flux;

/**
 * 影视AI服务
 *
 * @author hoxise
 * @since 2026/01/14 14:50:32
 */
public interface AiMovieChatService {
    /**
     * ai推荐
     *
     * @param userText 用户输入
     * @param chatId 聊天id
     * @param userid 用huid
     * @param mode 模式 对于deepseek 分chat和reasoner思维模式
     * @return 流式返回
     * @author hoxise
     * @since 2026/01/14 14:50:46
     *///AI推荐
    Flux<String> aiRecommend(String userText, String chatId, Long userid,String mode);

    /**
     * ai总结
     *
     * @param catalogid 目录id
     * @return 流式返回
     * @author hoxise
     * @since 2026/01/14 14:51:46
     *///AI总结
    Flux<String> aiSummary(Long catalogid);
}
