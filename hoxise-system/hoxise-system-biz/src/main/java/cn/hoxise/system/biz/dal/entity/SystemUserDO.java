package cn.hoxise.system.biz.dal.entity;

import cn.hoxise.common.base.enums.CommonStatusEnum;
import cn.hoxise.common.base.framework.mybatis.typehandler.StringListTypeHandler;
import cn.hoxise.common.base.pojo.BaseDO;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户表
 * @TableName sys_user
 */
@EqualsAndHashCode(callSuper = true)
@Schema(name = "用户表")
@TableName(value ="system_user")
@Data
@Builder
@AllArgsConstructor
public class SystemUserDO extends BaseDO implements Serializable {

    @TableId(value = "user_id",type = IdType.AUTO)
    private Long userId;

    /** 用户名 */
    private String userName;

    /** 密码 */
    private String password;

    /** 昵称 */
    private String nickName;

    /** 手机号 */
    private String phoneNumber;

    /** 最后登录时间 */
    private LocalDateTime lastLoginTime;

    @TableField(typeHandler = StringListTypeHandler.class)
    private List<String> roleIds;

    /** 状态,0正常 1(禁止登录) */
    private CommonStatusEnum status;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}