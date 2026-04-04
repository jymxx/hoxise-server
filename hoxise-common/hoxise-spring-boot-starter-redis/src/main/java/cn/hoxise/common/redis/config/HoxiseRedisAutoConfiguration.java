package cn.hoxise.common.redis.config;

import cn.hoxise.common.redis.utils.RedisUtil;
import cn.hutool.core.util.ReflectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisConfig
 *
 * @author hoxise
 * @since 2026/01/14 06:15:55
 */
@AutoConfiguration
@EnableCaching
@EnableConfigurationProperties({CacheProperties.class}) //spring.cache的配置属性
public class HoxiseRedisAutoConfiguration {

    /** redis注解缓存的序列化配置 */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory, CacheProperties cacheProperties) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        RedisCacheConfiguration defaultCacheConfig =
                RedisCacheConfiguration.defaultCacheConfig()
                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())) // 使用strSerializer对key进行数据类型转换
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(buildRedisSerializer())) // 使用jacksonSeial对value的数据类型进行转换
                        .entryTtl(cacheProperties.getRedis().getTimeToLive());
        return RedisCacheManager.builder(redisCacheWriter)
                .cacheDefaults(defaultCacheConfig)
                .build();
    }

    /** redis序列化 */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        //大多数情况，都是选用<String, Object>
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        //设置默认序列化方式
        template.setDefaultSerializer(buildRedisSerializer());
        //设置key序列化 这里用String
        template.setKeySerializer(new StringRedisSerializer());
        //设置val序列化方式为jackson
        template.setValueSerializer(buildRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(buildRedisSerializer());
        return template;
    }

    public static RedisSerializer<?> buildRedisSerializer() {
        RedisSerializer<Object> json = RedisSerializer.json();
        // 注册Java8的LocalDateTime 解决序列化问题
        ObjectMapper objectMapper = (ObjectMapper) ReflectUtil.getFieldValue(json, "mapper");
        objectMapper.registerModules(new JavaTimeModule());
        return json;
    }

    @Bean
    public RedisUtil redisUtil() {
        return new RedisUtil();
    }

}
