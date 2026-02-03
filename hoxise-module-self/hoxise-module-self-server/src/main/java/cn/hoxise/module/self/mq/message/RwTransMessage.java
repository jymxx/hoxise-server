package cn.hoxise.module.self.mq.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hoxise
 * @since 2026/2/3 下午5:42
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RwTransMessage {

    public static final String QUEUE = "QUEUE_RW_TRANS";

    public static final String EXCHANGE = "EXCHANGE_RW_TRANS";

    public static final String ROUTING_KEY = "ROUTING_KEY_RW_TRANS";

    /**
     * 预检查ID
     */
    private String preCheckId;

    /**
     * 包ID
     */
    private String packageId;

    /**
     * 作者
     */
    private String author;

    /**
     * 相对文件路径
     */
    private String path;

    /**
     * 本地文件路径
     */
    private String localPath;

    /**
     * 用户提示词
     */
    private String userPrompt;

}
