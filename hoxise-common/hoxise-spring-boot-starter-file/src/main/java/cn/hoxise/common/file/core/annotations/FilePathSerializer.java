package cn.hoxise.common.file.core.annotations;

import cn.hoxise.common.file.core.client.FileStorageClientFactory;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;

/**
 * 绝对路径序列化
 *
 * @author hoxise
 * @since 2026/01/14 06:42:47
 */
@Slf4j
@JsonComponent // 将序列化类注册到Jackson中
public class FilePathSerializer extends JsonSerializer<Object> {

    private static FileStorageClientFactory factory;
    @Autowired
    public void setFactory(FileStorageClientFactory factory) {
        FilePathSerializer.factory = factory;
    }

    @SneakyThrows
    @Override
    public void serialize(Object val, JsonGenerator gen, SerializerProvider serializers) {
        String objectName = val == null ? null : String.valueOf(val);
        // 如果是http开头的，直接返回
        if (StrUtil.isBlank(objectName) || objectName.startsWith("http")) {
            gen.writeObject(val);
            return;
        }
        if (factory == null){
            log.warn("序列化警告--factory为空");
            gen.writeObject(val);
        }
        // 直接访问的地址 配置了公共桶
        String presignedUrl = factory.getDefaultStorage().getAbsoluteUrl(objectName);
        gen.writeString(presignedUrl);
    }
}
