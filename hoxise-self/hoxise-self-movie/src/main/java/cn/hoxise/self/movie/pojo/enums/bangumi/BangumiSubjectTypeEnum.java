package cn.hoxise.self.movie.pojo.enums.bangumi;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author hoxise
 * @Description: Bangumi条目类型枚举
 * @Date 2025-12-23 上午8:37
 */
@Getter
@AllArgsConstructor
public enum BangumiSubjectTypeEnum {

    UNKNOWN(0, "未知"),
    BOOK(1, "书籍"),
    ANIME(2, "动画"),
    MUSIC(3, "音乐"),
    GAME(4, "游戏"),
    REAL(6, "三次元");

    private final Integer code;
    private final String name;

    /**
     * 根据code获取枚举值
     *
     * @param code 类型代码
     * @return 对应的枚举值，未找到时返回BOOK
     */
    public static BangumiSubjectTypeEnum getByCode(Integer code) {
        for (BangumiSubjectTypeEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return UNKNOWN;
    }

    /**
     * 根据code字符串获取枚举值
     *
     * @param code 类型代码字符串
     * @return 对应的枚举值，未找到时返回BOOK
     */
    public static BangumiSubjectTypeEnum getByCode(String code) {
        try {
            Integer codeInt = Integer.valueOf(code);
            return getByCode(codeInt);
        } catch (NumberFormatException e) {
            return UNKNOWN;
        }
    }
}