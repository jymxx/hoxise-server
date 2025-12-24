package cn.hoxise.system.biz.dal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 
 * @TableName system_sms
 */
@Schema(name = "短信日志")
@TableName(value ="system_sms_log")
@Data
@Builder
public class SystemSmsLogDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String phone;

    private String content;

    private String type;

    private LocalDateTime sendTime;

    private String requestId;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}