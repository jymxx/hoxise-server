package cn.hoxise.system.biz.dal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * SystemDictDO 系统字典DO
 *
 * @author hoxise
 * @since 2026/01/14 06:04:02
 */
@Schema(description = "字典")
@TableName(value ="system_dict")
@Data
public class SystemDictDO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private String dictKey;

    /**
     * 值
     */
    private String dictValue;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}