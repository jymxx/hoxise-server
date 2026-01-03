package cn.hoxise.self.movie.pojo.enums.bangumi;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Bangumi 用户组枚举
 * 1 = 管理员
 * 2 = Bangumi 管理猿
 * 3 = 天窗管理猿
 * 4 = 禁言用户
 * 5 = 禁止访问用户
 * 8 = 人物管理猿
 * 9 = 维基条目管理猿
 * 10 = 用户
 * 11 = 维基人
 *
 * @Author hoxise
 * @Date 2025-12-23 上午8:48
 */
@Getter
@AllArgsConstructor
public enum BangumiUserGroupEnum {

    ADMIN(1, "管理员"),
    BANGUMI_MANAGER(2, "Bangumi 管理猿"),
    WINDOW_MANAGER(3, "天窗管理猿"),
    SILENCED_USER(4, "禁言用户"),
    BANNED_USER(5, "禁止访问用户"),
    CHARACTER_MANAGER(8, "人物管理猿"),
    WIKI_MANAGER(9, "维基条目管理猿"),
    USER(10, "用户"),
    WIKI_USER(11, "维基人");

    private final Integer code;
    private final String name;

    /**
     * 根据code获取枚举值
     *
     * @param code 用户组代码
     * @return 对应的枚举值，未找到时返回USER
     */
    public static BangumiUserGroupEnum getByCode(Integer code) {
        for (BangumiUserGroupEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return USER;
    }
}
