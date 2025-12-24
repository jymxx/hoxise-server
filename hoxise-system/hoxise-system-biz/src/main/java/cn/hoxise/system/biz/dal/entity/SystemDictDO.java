package cn.hoxise.system.biz.dal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * @TableName system_dict
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}