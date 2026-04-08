package cn.hoxise.module.movie.mq.message;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 自动匹配消息
 *
 * @author hoxise
 * @since 2026/3/13
 */
@Data
@AllArgsConstructor
public class AutoMatchMessage implements Serializable {

    public static final String QUEUE = "QUEUE_MOVIE_AUTO_MATCH";

    public static final String EXCHANGE = "EXCHANGE_MOVIE_AUTO_MATCH";

    public static final String ROUTING_KEY = "ROUTING_KEY_MOVIE_AUTO_MATCH";

    /**
     * 登录用户 ID
     */
    private Long loginId;
}
