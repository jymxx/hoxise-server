package cn.hoxise.common.file.core.annotations;

import cn.hoxise.common.file.utils.FileStorageUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 绝对路径序列化
 *
 * @author hoxise
 * @since 2026/01/14 06:42:47
 */
@Slf4j
public class FilePathSerializer extends JsonSerializer<Object> {

    @SneakyThrows
    @Override
    public void serialize(Object val, JsonGenerator gen, SerializerProvider serializers) {
        String objectName = val==null?null:String.valueOf(val);
        if (objectName == null){
            gen.writeString("");
            return;
        }
        //不匹配http开头的
        if (objectName.startsWith("http")){
            gen.writeString(objectName);
            return;
        }
        try{
            String presignedUrl = FileStorageUtil.getAbsoluteUrl(objectName);
            gen.writeString(presignedUrl);
        }catch (Exception e){
            gen.writeString(objectName);
            log.error("文件路径序列化错误:{}",e.getMessage());
        }
    }
}
