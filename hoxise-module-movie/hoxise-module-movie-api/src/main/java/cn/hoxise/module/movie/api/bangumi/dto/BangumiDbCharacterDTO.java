package cn.hoxise.module.movie.api.bangumi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


/**
 * MovieDbBangumiCharacterDTO
 *
 * @author hoxise
 * @since 2026/01/28 12:31:44
 */
@Schema(description = "Bangumi 角色")
@Data
public class BangumiDbCharacterDTO {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 目录
     */
    private Long catalogid;

    /**
     * Bangumi ID
     */
    private Long characterId;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色图片URL
     */
    private String imgUrl;

    /**
     * 角色关系，如主角、配角等
     */
    private String relation;

    /**
     * 角色类型，标识角色的类型（如人物、生物等）
     */
    private Integer type;

    /**
     * 角色关联的声优ID列表，以逗号分隔
     */
    private List<String> actors;

}