package cn.hoxise.self.biz.dal.entity;

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
 * Bangumi演员/声优表
 * @TableName movie_db_bangumi_actor
 */
@TableName(value ="movie_db_bangumi_actor")
@Data
@Builder
@AllArgsConstructor
public class MovieDbBangumiActorDO implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
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
    @TableField(typeHandler = StringListTypeHandler.class)
    private List<String> career;

    /**
     * 演员类型
     */
    private Integer type;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}