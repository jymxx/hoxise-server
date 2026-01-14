package cn.hoxise.common.file.core.annotations;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 将存储文件的相对路径名称序列化转成可直接访问的绝对路径
 * 只适用于jackson序列化方式
 *
 * @author hoxise
 * @since 2026/01/14 06:43:04
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = FilePathSerializer.class)
public @interface FilePathFormat {

}
