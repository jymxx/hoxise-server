package cn.hoxise.common.redis.utils;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author hoxise
 * @since 2026/01/14 06:47:36
 */
public class RedisUtil {

    @Resource private StringRedisTemplate stringRedisTemplate;

    @Resource private RedisTemplate<String, Object> redisTemplate;
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
    }

    public void setValue(String key, String value) {
        stringRedisTemplate.boundValueOps(key).set(value);
    }

    public void setValue(String key, String value, long time, TimeUnit timeUnit) {
        stringRedisTemplate.boundValueOps(key).set(value, time, timeUnit);
    }

    public String getValue(String key) {
        return stringRedisTemplate.boundValueOps(key).get();
    }

    public Boolean setObject(String key, Object value) {
        redisTemplate.boundValueOps(key).set(value);
        return true;
    }

    public Boolean setObject(String key, Object value, long time, TimeUnit timeUnit) {
        redisTemplate.boundValueOps(key).set(value, time, timeUnit);
        return true;
    }

    public Object getObject(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    public void increment(String key) {
        redisTemplate.boundValueOps(key).increment();
    }

    /**
     * 清理redis
     */
    public void clear() {
        Set<String> keys = redisTemplate.keys("*");
        if (ObjectUtil.isNotNull(keys)) {
            redisTemplate.delete(keys);
        }
    }
}
