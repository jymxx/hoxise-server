package cn.hoxise.module.self.pojo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 翻译期间状态
 *
 * @author hoxise
 * @since 2026/2/3 下午5:46
 */
@Getter
@AllArgsConstructor
public enum RwTransStatus {

    check(0,"预检"),
    translating(1,"翻译中"),
    complete(2,"完成");

    final Integer code;
    final String desc;

    public static RwTransStatus getByCode(Integer code){
        for (RwTransStatus value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
