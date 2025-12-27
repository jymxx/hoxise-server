package cn.hoxise.common.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Author hoxise
 * @Description: 属性
 * @Date 2025-12-26 上午4:47
 */
@Data
@ConfigurationProperties(prefix = "spring.ai")
public class OpenAiProperties {
    private OpenAi openai = new OpenAi();
    private Vectorstore vectorstore = new Vectorstore();

    @Data
    public static class OpenAi {
        private String apiKey;
        private String baseUrl;
        private ChatOptions chat = new ChatOptions();

        @Data
        public static class ChatOptions {
            private Options options = new Options();

            @Data
            public static class Options {
                private String model;
                private Double temperature;
                private Integer maxTokens;
            }
        }
    }

    @Data
    public static class Vectorstore {
        private boolean enabled;
        private Redis redis = new Redis();
        private EmbeddingModel embeddingModel = new EmbeddingModel();

        @Data
        public static class EmbeddingModel {
            private String apiKey;
            private String baseUrl;
            private String model;
        }

        @Data
        public static class Redis {
            private boolean initializeSchema;
            private String indexName;
            private String prefix;
        }
    }
}