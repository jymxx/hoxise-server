package cn.hoxise.system.biz.dal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 系统角色
 *
 * @author hoxise
 * @since 2026/01/14 06:07:15
 */
@Schema(name = "角色表")
@TableName(value ="system_role")
@Data
public class SystemRoleDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色
     */
    private String roleName;

    /**
     * 描述
     */
    private String description;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}