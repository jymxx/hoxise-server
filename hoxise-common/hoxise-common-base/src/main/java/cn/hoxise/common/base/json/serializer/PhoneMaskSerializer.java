package cn.hoxise.common.base.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.SneakyThrows;

/**
 * 手机号脱敏序列化器
 * 将手机号中间 4 位替换为 ****
 * 13812345678 -> 138****5678
 *
 * @author hoxise
 * @since 2026/04/03
 */
public class PhoneMaskSerializer extends JsonSerializer<String> {

    @SneakyThrows
    @Override
    public void serialize(String phone, JsonGenerator gen, SerializerProvider serializers) {
        if (phone == null || phone.length() != 11) {
            gen.writeString(phone);
            return;
        }
        // 正则脱敏：13812345678 -> 138****5678
        String masked = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        gen.writeString(masked);
    }
}
