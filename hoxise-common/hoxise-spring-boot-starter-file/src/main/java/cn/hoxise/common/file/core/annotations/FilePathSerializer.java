package cn.hoxise.common.file.core.annotations;

import cn.hoxise.common.file.core.client.FileStorageClientFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 绝对路径序列化
 *
 * @author hoxise
 * @since 2026/01/14 06:42:47
 */
@Slf4j
@Component
public class FilePathSerializer extends JsonSerializer<Object> {

    @Resource private FileStorageClientFactory fileStorageStrategyFactory;

    @SneakyThrows
    @Override
    public void serialize(Object val, JsonGenerator gen, SerializerProvider serializers) {
        String objectName = val == null ? null:String.valueOf(val);
        if (objectName == null){
            gen.writeString("");
            return;
        }
        //不匹配http开头的
        if (objectName.startsWith("http")){
            gen.writeString(objectName);
            return;
        }
        //直接访问的地址
        String presignedUrl = fileStorageStrategyFactory.getDefaultStorage().getPresignedUrl(objectName);
        gen.writeString(presignedUrl);
    }
}
