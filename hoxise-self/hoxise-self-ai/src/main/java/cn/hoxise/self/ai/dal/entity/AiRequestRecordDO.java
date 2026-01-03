package cn.hoxise.self.ai.dal.entity;

import cn.hoxise.self.ai.pojo.enums.AiMethodEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName ai_request_record
 */
@TableName(value ="ai_request_record")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AiRequestRecordDO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private Long userid;

    /**
     * 请求时间
     */
    private LocalDateTime requestTime;

    /**
     * 请求接口
     */
    private AiMethodEnum method;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}