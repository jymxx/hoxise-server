package cn.hoxise.common.base.json.annotation;

import cn.hoxise.common.base.json.serializer.PhoneMaskSerializer;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 手机号脱敏注解
 * 序列化时将手机号中间 4 位替换为 ****
 * 只适用于 Jackson 序列化方式
 *
 * @author hoxise
 * @since 2026/04/03
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside // 标记这是 Jackson 注解容器
@JsonSerialize(using = PhoneMaskSerializer.class)
public @interface PhoneMask {
}
