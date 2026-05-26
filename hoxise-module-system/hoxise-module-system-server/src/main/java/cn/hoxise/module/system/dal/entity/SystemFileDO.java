package cn.hoxise.module.system.dal.entity;

import cn.hoxise.common.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serial;

/**
 * 文件记录表
 *
 * @author hoxise
 * @since 2026/04/12
 */
@TableName("system_file")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemFileDO extends BaseDO {

    @Serial
    @TableField
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * OSS存储路径
     */
    private String objectName;

    /**
     * 原始文件名
     */
    private String originalName;

    /**
     * 文件大小(字节)
     */
    private Long fileSize;

    /**
     * MIME类型
     */
    private String fileType;

    /**
     * 扩展名
     */
    private String extension;

    /**
     * 绑定状态: 0-未绑定 1-已绑定
     */
    private Integer bindStatus;
}