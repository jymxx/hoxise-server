package cn.hoxise.self.movie.controller.movie.vo;

import cn.hoxise.common.file.annotations.FilePathFormat;
import cn.hoxise.self.movie.dal.entity.MovieDbBangumiActorDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author hoxise
 * @Description: 角色信息返回类
 * @Date 2025-12-23 下午5:17
 */
@Data
@Builder
@AllArgsConstructor
public class MovieCharactersVO {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
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
    private List<MovieDbBangumiActorDO> actors;
}
