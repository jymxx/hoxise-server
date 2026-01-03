package cn.hoxise.self.movie.pojo.dto;

import lombok.Data;
import java.util.List;

/**
 * Bangumi剧集信息响应类
 * 用于接收Bangumi API中剧集数据的响应结果
 */
@Data
public class BangumiEpisodesResponse {

    /**
     * 剧集信息列表
     */
    private List<EpisodeInfo> data;

    /**
     * 总数量
     */
    private Integer total;

    /**
     * 限制数量
     */
    private Integer limit;

    /**
     * 偏移量
     */
    private Integer offset;

    /**
     * 剧集信息类
     * 描述单个剧集的详细信息
     */
    @Data
    public static class EpisodeInfo {
        /**
         * 播放日期
         */
        private String airdate;

        /**
         * 原始名称
         */
        private String name;

        /**
         * 中文名称
         */
        private String name_cn;

        /**
         * 持续时间
         */
        private String duration;

        /**
         * 描述信息
         */
        private String desc;

        /**
         * 集数
         */
        private Integer ep;

        /**
         * 排序
         */
        private Integer sort;

        /**
         * 剧集ID
         */
        private Integer id;

        /**
         * 主题ID
         */
        private Integer subject_id;

        /**
         * 评论数
         */
        private Integer comment;

        /**
         * 类型
         */
        private Integer type;

        /**
         * 磁盘
         */
        private Integer disc;

        /**
         * 持续时间（秒）
         */
        private Integer duration_seconds;
    }
}
