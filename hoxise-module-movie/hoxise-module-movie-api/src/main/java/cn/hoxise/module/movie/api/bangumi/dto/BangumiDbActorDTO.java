package cn.hoxise.module.movie.api.bangumi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


/**
 * MovieDbBangumiActorDTO
 *
 * @author hoxise
 * @since 2026/01/28 12:32:28
 */
@Schema(description = "Bangumi 演员/声优")
@Data
public class BangumiDbActorDTO {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * Bangumi ID
     */
    private Long actorId;

    /**
     * 演员/声优姓名
     */
    private String name;

    /**
     * 演员头像图片URL
     */
    private String imgUrl;

    /**
     * 演员简介，包含演员的基本信息和履历
     */
    private String shortSummary;

    /**
     * 职业生涯列表，以逗号分隔的职业类型，如["artist","seiyu","actor"]
     */
    private List<String> career;

    /**
     * 演员类型
     */
    private Integer type;

}