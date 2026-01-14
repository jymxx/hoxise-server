package cn.hoxise.common.ai.config.openai;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.memory.repository.jdbc.MysqlChatMemoryRepositoryDialect;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.TokenCountBatchingStrategy;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import redis.clients.jedis.JedisPooled;

/**
 * OpenAiAutoConfig
 *
 * @author hoxise
 * @since 2026/01/14 07:14:46
 */
@AutoConfiguration
@EnableConfigurationProperties({OpenAiProperties.class, RedisProperties.class})
public class OpenAiAutoConfig {

    /**
     * ChatModel
     */
    @Bean
    public OpenAiChatModel.Builder chatModel(OpenAiProperties openAiProperties) {
        return OpenAiChatModel.builder()
                .openAiApi(OpenAiApi.builder().apiKey(openAiProperties.getOpenai().getApiKey())
                        .baseUrl(openAiProperties.getOpenai().getBaseUrl()).build())
                .defaultOptions(OpenAiChatOptions.builder()
                        .model(openAiProperties.getOpenai().getChat().getOptions().getModel())
                        .temperature(openAiProperties.getOpenai().getChat().getOptions().getTemperature())
                        .maxTokens(openAiProperties.getOpenai().getChat().getOptions().getMaxTokens())
                        .build());
    }

    /**
     * ChatClient
     */
    @Bean
    public ChatClient.Builder chatClient(OpenAiChatModel.Builder chatModelBuilder) {
        return ChatClient.builder(chatModelBuilder.build());
    }

    /**
     * chatMemory
     */
    @Bean
    public ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository) {
        return  MessageWindowChatMemory.builder()
//                .chatMemoryRepository(new InMemoryChatMemoryRepository()) //默认用内存管理
                .chatMemoryRepository(jdbcChatMemoryRepository) //配置数据库存储接口
                .maxMessages(10) //存储历史对话数量 默认20
                .build();
    }
    /**
     * JdbcChatMemoryRepository
     */
    @Bean
    public JdbcChatMemoryRepository jdbcChatMemoryRepository(JdbcTemplate jdbcTemplate) {
        return JdbcChatMemoryRepository.builder()
                .jdbcTemplate(jdbcTemplate)
                .dialect(new MysqlChatMemoryRepositoryDialect())//对应数据库类型
                .build();
    }


    //############   向量数据库    ##########
    /**
     * Redis
     */
    @Bean
    public JedisPooled jedisPooled(RedisProperties redisProperties) {
        return new JedisPooled(redisProperties.getHost(), redisProperties.getPort()
                , StrUtil.isBlank(redisProperties.getUser())?null:redisProperties.getUser()
                , StrUtil.isBlank(redisProperties.getPassword())?null: redisProperties.getPassword());
    }
    /**
     * EmbeddingModel 向量模型
     */
    @Bean
    public EmbeddingModel embeddingModel(OpenAiProperties openAiProperties) {
        return new OpenAiEmbeddingModel(OpenAiApi.builder().apiKey(openAiProperties.getVectorStore().getEmbeddingModel().getApiKey())
                .baseUrl(openAiProperties.getVectorStore().getEmbeddingModel().getBaseUrl()).build()
                , MetadataMode.EMBED
                , OpenAiEmbeddingOptions.builder().model(openAiProperties.getVectorStore().getEmbeddingModel().getModel()).build()
        );
    }
    /**
     * VectorStore
     */
    @Bean
    public RedisVectorStore vectorStore(JedisPooled jedisPooled, EmbeddingModel embeddingModel,OpenAiProperties openAiProperties) {
        return RedisVectorStore.builder(jedisPooled, embeddingModel)
                .indexName(openAiProperties.getVectorStore().getRedis().getIndexName())                // Optional: defaults to "spring-ai-index"
                .prefix(openAiProperties.getVectorStore().getRedis().getPrefix())                  // Optional: defaults to "embedding:"
                .metadataFields(                         // Optional: define metadata fields for filtering
                        RedisVectorStore.MetadataField.numeric("id"),
                        RedisVectorStore.MetadataField.text("name"),
                        RedisVectorStore.MetadataField.text("originName"),
                        RedisVectorStore.MetadataField.tag("tags"),
                        RedisVectorStore.MetadataField.tag("metaTags"),
                        RedisVectorStore.MetadataField.text("type"),
                        RedisVectorStore.MetadataField.text("characters"),
                        RedisVectorStore.MetadataField.text("actors"),
                        RedisVectorStore.MetadataField.numeric("releaseYear"))
                .initializeSchema(openAiProperties.getVectorStore().getRedis().isInitializeSchema())                   // Optional: defaults to false
                .batchingStrategy(new TokenCountBatchingStrategy()) // Optional: defaults to TokenCountBatchingStrategy
                .build();
    }


}
