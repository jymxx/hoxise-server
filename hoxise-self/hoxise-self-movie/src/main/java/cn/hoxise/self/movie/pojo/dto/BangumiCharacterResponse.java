package cn.hoxise.self.movie.pojo.dto;

import lombok.Data;
import java.util.List;

/**
 * Bangumi角色信息响应类
 * 用于接收Bangumi API中角色数据的响应结果
 *
 * @Author hoxise
 * @Date 2025-12-23
 */
@Data
public class BangumiCharacterResponse {
    /**
     * 角色信息列表
     * 包含动画/漫画等作品中的角色详情
     */
    private List<CharacterInfo> data;

    /**
     * 角色信息类
     * 描述单个角色的基本信息和关联的声优信息
     */
    @Data
    public static class CharacterInfo {
        /**
         * 角色图片信息
         * 包含不同尺寸的图片链接
         */
        private Images images;

        /**
         * 角色名称
         */
        private String name;

        /**
         * 角色关系
         * 如"主角"、"配角"等角色在作品中的定位
         */
        private String relation;

        /**
         * 声优信息列表
         * 演绎该角色的声优/演员信息
         */
        private List<ActorInfo> actors;

        /**
         * 角色类型
         * 标识角色的类型（如人物、生物等）
         */
        private Integer type;

        /**
         * 角色ID
         * Bangumi系统中该角色的唯一标识符
         */
        private Integer id;
    }

    /**
     * 图片信息类
     * 包含不同尺寸的图片URL
     */
    @Data
    public static class Images {
        /**
         * 小尺寸图片URL
         */
        private String small;

        /**
         * 网格视图图片URL
         */
        private String grid;

        /**
         * 大尺寸图片URL
         */
        private String large;

        /**
         * 中等尺寸图片URL
         */
        private String medium;
    }

    /**
     * 演员/声优信息类
     * 描述演绎角色的演员或声优的详细信息
     */
    @Data
    public static class ActorInfo {
        /**
         * 演员头像图片信息
         */
        private Images images;

        /**
         * 演员/声优姓名
         */
        private String name;

        /**
         * 演员简介
         * 包含演员的基本信息和履历
         */
        private String short_summary;

        /**
         * 职业生涯列表
         * 演员从事的职业类型，如["artist", "seiyu", "actor"]
         */
        private List<String> career;

        /**
         * 演员ID
         * Bangumi系统中该演员的唯一标识符
         */
        private Integer id;

        /**
         * 演员类型
         */
        private Integer type;

        /**
         * 是否锁定
         * 表示该条目是否被锁定编辑
         */
        private Boolean locked;
    }
}
