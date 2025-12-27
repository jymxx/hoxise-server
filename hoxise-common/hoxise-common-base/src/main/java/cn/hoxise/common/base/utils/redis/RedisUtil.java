package cn.hoxise.common.base.utils.redis;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: hoxise
 * @Description: redis工具
 * @Date: 2023/10/14 16:44
 */
@Component
public class RedisUtil {

    private static StringRedisTemplate stringRedisTemplate;
    private static RedisTemplate<String,Object> redisTemplate;
    private static long redisTimeout;

    @Value("${project.redisTimeout:12}")
    public void setRedisTimeout(long timeout) {
        RedisUtil.redisTimeout = timeout;
    }

    @Resource
    public void setStringRedisTemplate(StringRedisTemplate template) {
        RedisUtil.stringRedisTemplate = template;
    }
    @Resource
    public void setRedisTemplate(RedisTemplate<String,Object> template) {
        RedisUtil.redisTemplate = template;
    }

    public static Boolean setValue(String key, String value) {
        stringRedisTemplate.boundValueOps(key).set(value, redisTimeout, TimeUnit.HOURS);
        return true;
    }

    public static Boolean setValue(String key, String value, long time, TimeUnit timeUnit) {
        stringRedisTemplate.boundValueOps(key).set(value, time, timeUnit);
        return true;
    }

    public static String getValue(String key) {
        return stringRedisTemplate.boundValueOps(key).get();
    }

    public static Boolean setObject(String key, Object value) {
        redisTemplate.boundValueOps(key).set(value, redisTimeout, TimeUnit.HOURS);
        return true;
    }

    public static Boolean setObject(String key, Object value, long time, TimeUnit timeUnit) {
        redisTemplate.boundValueOps(key).set(value, time, timeUnit);
        return true;
    }

    public static Object getObject(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    public static void increment(String key) {
        redisTemplate.boundValueOps(key).increment();
    }

    /** 清理redis **/
    public static void clear() {
        Set<String> keys = redisTemplate.keys(Constants.ASTERISK);
        if (ObjectUtil.isNotNull(keys)) {
            redisTemplate.delete(keys);
        }
    }
}

