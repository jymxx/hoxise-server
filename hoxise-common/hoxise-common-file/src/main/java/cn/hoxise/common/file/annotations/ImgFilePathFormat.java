package cn.hoxise.common.file.annotations;

import cn.hutool.core.img.Img;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author hoxise
 * @Description: 图片地址序列化 可以直接预览而不是下载
 * @Date 2025-12-22 下午6:36
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = ImgFilePathSerializer.class)
public @interface ImgFilePathFormat {
}
