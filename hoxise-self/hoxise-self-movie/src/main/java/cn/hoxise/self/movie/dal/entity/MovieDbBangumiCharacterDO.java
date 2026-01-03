package cn.hoxise.self.movie.dal.entity;

import cn.hoxise.common.base.framework.mybatis.typehandler.StringListTypeHandler;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 电影数据库角色表
 * @TableName movie_db_bangumi_character
 */
@TableName(value ="movie_db_bangumi_character")
@Data
@Builder
@AllArgsConstructor
public class MovieDbBangumiCharacterDO implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
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
    @TableField(typeHandler = StringListTypeHandler.class)
    private List<String> actors;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}