package cn.hoxise.common.file.annotations;

import cn.hoxise.common.file.pojo.enums.FileTypeEnum;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author tangxin
 * @Description: 将存储文件的相对路径名称序列化转成可直接访问的绝对路径
 * 只适用于jackson序列化方式
 * @Date 2024-07-16 下午2:12
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = FilePathSerializer.class)
public @interface FilePathFormat {

}
