package cn.hoxise.module.movie.enums.bangumi;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Bangumi 章节放送状态枚举
 *
 * @author hoxise
 * @since 2026/01/14 15:00:13
 */
@Getter
@AllArgsConstructor
public enum BangumiEpisodeStatusEnum {

    AIR("Air", "已放送"),
    TODAY("Today", "正在放送"),
    NA("NA", "未放送");

    private final String code;
    private final String name;

    /**
     * 根据code获取枚举值
     *
     * @param code 状态代码
     * @return 对应的枚举值，未找到时返回AIR
     */
    public static BangumiEpisodeStatusEnum getByCode(String code) {
        for (BangumiEpisodeStatusEnum value : values()) {
            if (value.code.equalsIgnoreCase(code)) {
                return value;
            }
        }
        return AIR;
    }
}
