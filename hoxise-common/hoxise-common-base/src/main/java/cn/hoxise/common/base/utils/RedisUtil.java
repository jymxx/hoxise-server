package cn.hoxise.common.base.utils;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * @Author: hoxise
 * @Description: redis工具
 * @Date: 2023/10/14 16:44
 */
@Component
public class RedisUtil {

    @Resource private StringRedisTemplate stringRedisTemplate;

    @Resource private RedisTemplate<String,Object> redisTemplate;

    @Value("${project.redisTimeout}")
    private long redisTimeout;

    /** 
     * @Description: 设置字符串值,使用配置的默认redis过期时间
     * @param key 
     * @param value 
     * @return: java.lang.Boolean 
     * @author: hoxise
     * @date: 2024/1/18 21:01
     */
    public Boolean setValue(String key,String value){
        stringRedisTemplate.boundValueOps(key).set(value,redisTimeout, TimeUnit.HOURS);
        return true;
    }
    
    public Boolean setValue(String key,String value,long time, TimeUnit timeUnit){
        stringRedisTemplate.boundValueOps(key).set(value,time,timeUnit);
        return true;
    }
    
    public String getValue(String key){
        return stringRedisTemplate.boundValueOps(key).get();
    }

    /** 
     * @Description: 设置对象值,使用配置的默认redis过期时间
     * @param key 
     * @param value
     * @return: java.lang.Boolean 
     * @author: hoxise
     * @date: 2024/1/18 21:01
     */
    public Boolean setObject(String key,Object value){
        redisTemplate.boundValueOps(key).set(value,redisTimeout, TimeUnit.HOURS);
        return true;
    }

    public Boolean setObject(String key,Object value,long time, TimeUnit timeUnit){
        redisTemplate.boundValueOps(key).set(value,time,timeUnit);
        return true;
    }

    public Object getObject(String key){
        return redisTemplate.boundValueOps(key).get();
    }
}
