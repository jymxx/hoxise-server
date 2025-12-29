package cn.hoxise.self.biz.pojo.enums.bangumi;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Bangumi 章节类型枚举
 * 0 = 本篇
 * 1 = 特别篇
 * 2 = OP
 * 3 = ED
 * 4 = 预告/宣传/广告
 * 5 = MAD
 * 6 = 其他
 *
 * @Author hoxise
 * @Date 2025-12-23 上午8:48
 */
@Getter
@AllArgsConstructor
public enum BangumiEpisodeTypeEnum {

    MAIN(0, "本篇"),
    SPECIAL(1, "特别篇"),
    OP(2, "OP"),
    ED(3, "ED"),
    PROMOTION(4, "预告/宣传/广告"),
    MAD(5, "MAD"),
    OTHER(6, "其他");

    private final Integer code;
    private final String name;

    /**
     * 根据code获取枚举值
     *
     * @param code 类型代码
     * @return 对应的枚举值，未找到时返回MAIN
     */
    public static BangumiEpisodeTypeEnum getByCode(Integer code) {
        for (BangumiEpisodeTypeEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return MAIN;
    }

    /**
     * 根据code字符串获取枚举值
     *
     * @param code 类型代码字符串
     * @return 对应的枚举值，未找到时返回MAIN
     */
    public static BangumiEpisodeTypeEnum getByCode(String code) {
        try {
            Integer codeInt = Integer.valueOf(code);
            return getByCode(codeInt);
        } catch (NumberFormatException e) {
            return MAIN;
        }
    }
}
