package cn.hoxise.module.movie.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 播放信息 DTO
 *
 * @author hoxise
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayUrlDTO {

    /**
     * 总集数
     */
    private Integer totalEpisodes;

    /**
     * 子数据列表
     */
    private List<EpisodeItem> episodes;

    /**
     * 子数据：单集信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EpisodeItem {

        /**
         * 章节号
         */
        private Integer episode;

        /**
         * 名称
         */
        private String name;

        /**
         * 播放地址
         */
        private String url;

        /**
         * 分辨率
         */
        private String resolution;

        /**
         * 文件大小（字节）
         */
        private Long fileSize;
    }
}