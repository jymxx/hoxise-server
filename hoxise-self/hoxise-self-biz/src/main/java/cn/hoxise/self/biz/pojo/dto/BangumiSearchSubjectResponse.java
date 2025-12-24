package cn.hoxise.self.biz.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @Author hoxise
 * @Description: Bangumi查询结果
 * @Date 2025-12-23 上午9:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BangumiSearchSubjectResponse {
    /**
     * 搜索结果数据列表
     */
    private List<Subject> data;

    /**
     * Bangumi条目信息
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Subject {
        /**
         * 发布日期
         */
        private String date;
        /**
         * 发布平台（如：漫画、小说、TV等）
         */
        private String platform;
        /**
         * 图片信息
         */
        private Images images;
        /**
         * 封面图片URL
         */
        private String image;
        /**
         * 简介
         */
        private String summary;
        /**
         * 原名
         */
        private String name;
        /**
         * 中文名
         */
        private String name_cn;
        /**
         * 标签列表
         */
        private List<Tag> tags;
        /**
         * 信息框数据
         */
        private List<InfoboxItem> infobox;
        /**
         * 评分信息
         */
        private Rating rating;
        /**
         * 收藏信息
         */
        private Collection collection;
        /**
         * 条目ID
         */
        private Integer id;
        /**
         * 集数
         */
        private Integer eps;
        /**
         * 元标签列表
         */
        private List<String> meta_tags;
        /**
         * 卷数
         */
        private Integer volumes;
        /**
         * 是否为系列
         */
        private Boolean series;
        /**
         * 是否锁定
         */
        private Boolean locked;
        /**
         * 是否为NSFW内容
         */
        private Boolean nsfw;
        /**
         * 条目类型（如：1=漫画, 2=动画, 3=音乐等）
         */
        private Integer type;

        /**
         * 总集数
         */
        private Integer total_episodes;
    }

    /**
     * 图片信息
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Images {
        /**
         * 小图URL
         */
        private String small;
        /**
         * 网格图URL
         */
        private String grid;
        /**
         * 大图URL
         */
        private String large;
        /**
         * 中图URL
         */
        private String medium;
        /**
         * 普通图URL
         */
        private String common;
    }

    /**
     * 标签信息
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Tag {
        /**
         * 标签名
         */
        private String name;
        /**
         * 使用次数
         */
        private Integer count;
        /**
         * 总计内容数
         */
        private Integer total_cont;
    }

    /**
     * 信息框项目
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InfoboxItem {
        /**
         * 属性名
         */
        private String key;
        /**
         * 属性值，可能是字符串或对象数组，使用 Object 类型
         */
        private Object value;
    }

    /**
     * 评分信息
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Rating {
        /**
         * 排名
         */
        private Integer rank;
        /**
         * 评分总数
         */
        private Integer total;
        /**
         * 各分数段人数统计
         */
        private Map<String, Integer> count;
        /**
         * 平均评分
         */
        private Double score;
    }

    /**
     * 收藏信息
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Collection {
        /**
         * 搁置数量
         */
        private Integer on_hold;
        /**
         * 抛弃数量
         */
        private Integer dropped;
        /**
         * 想做数量
         */
        private Integer wish;
        /**
         * 做过数量
         */
        private Integer collect;
        /**
         * 在做数量
         */
        private Integer doing;
    }
}
