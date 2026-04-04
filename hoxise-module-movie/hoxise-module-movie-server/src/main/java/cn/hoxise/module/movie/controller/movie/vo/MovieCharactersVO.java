package cn.hoxise.module.movie.controller.movie.vo;

import cn.hoxise.common.file.core.annotations.FilePathFormat;
import cn.hoxise.module.movie.dal.entity.BangumiDbActorDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 角色信息返回类
 *
 * @author hoxise
 * @since 2026/01/14 14:54:58
 */
@Data
@Builder
@AllArgsConstructor
public class MovieCharactersVO {

    /**
     * 主键ID
     */
    private Long id;

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
    @FilePathFormat
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
     * 角色关联的声优ID
     */
    private List<BangumiDbActorDO> actors;
}
