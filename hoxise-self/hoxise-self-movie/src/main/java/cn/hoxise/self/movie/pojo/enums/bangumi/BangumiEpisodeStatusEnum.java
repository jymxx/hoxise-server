package cn.hoxise.self.movie.pojo.enums.bangumi;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Bangumi 章节放送状态枚举
 * Air = 已放送
 * Today = 正在放送
 * NA = 未放送
 *
 * @Author hoxise
 * @Date 2025-12-23 上午8:48
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
