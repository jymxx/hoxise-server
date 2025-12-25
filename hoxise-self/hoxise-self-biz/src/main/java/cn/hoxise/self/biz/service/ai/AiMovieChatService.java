package cn.hoxise.self.biz.service.ai;

import reactor.core.publisher.Flux;

/**
 * @Author hoxise
 * @Description: 影视聊天
 * @Date 2025-12-25 上午4:30
 */
public interface AiMovieChatService {
    Flux<String> aiSummary(Long catalogid);
}
