package cn.hoxise.module.ai.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * OpenAiProperties
 *
 * @author hoxise
 * @since 2026/01/14 07:14:54
 */
@Data
@ConfigurationProperties(prefix = "spring.ai")
public class OpenAiProperties {
    private OpenAi openai = new OpenAi();
    private Vectorstore vectorStore = new Vectorstore();

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