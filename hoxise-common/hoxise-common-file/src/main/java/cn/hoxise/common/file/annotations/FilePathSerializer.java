package cn.hoxise.common.file.annotations;

import cn.hoxise.common.file.api.FileStorageApi;
import cn.hoxise.common.file.pojo.enums.FileTypeEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import cn.hoxise.common.file.utils.FileStorageUtil;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author tangxin
 * @Description: 绝对路径序列化
 * @Date 2024-07-16 下午2:23
 */
@Component
@Slf4j
public class FilePathSerializer extends JsonSerializer<Object> {

    @Resource private FileStorageApi fileStorageApi;

    @SneakyThrows
    @Override
    public void serialize(Object val, JsonGenerator gen, SerializerProvider serializers) {
        String objectName = val==null?null:String.valueOf(val);
        if (objectName == null || fileStorageApi == null){
            gen.writeString("");
            return;
        }
        //不匹配http开头的
        if (objectName.startsWith("http")){
            gen.writeString(objectName);
            return;
        }
        try{
            String presignedUrl = fileStorageApi.getAbsoluteUrl(objectName);
            gen.writeString(Objects.requireNonNullElse(presignedUrl, objectName));
        }catch (Exception e){
            gen.writeString(objectName);
            log.error("文件路径序列化错误:{}",e.getMessage());
        }
    }
}
