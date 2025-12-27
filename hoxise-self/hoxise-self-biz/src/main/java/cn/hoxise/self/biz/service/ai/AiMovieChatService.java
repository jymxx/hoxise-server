package cn.hoxise.self.biz.service.ai;

import reactor.core.publisher.Flux;

/**
 * @Author hoxise
 * @Description: 影视聊天
 * @Date 2025-12-25 上午4:30
 */
public interface AiMovieChatService {
    //AI推荐
    Flux<String> aiRecommend(String userText, String chatId, Long userid,String mode);

    //AI总结
    Flux<String> aiSummary(Long catalogid);
}
