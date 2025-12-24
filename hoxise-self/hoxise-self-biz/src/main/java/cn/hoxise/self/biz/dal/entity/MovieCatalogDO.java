package cn.hoxise.self.biz.dal.entity;

import cn.hoxise.self.biz.pojo.enums.MovieStatusEnum;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 
 * @TableName movie_catalog
 */
@Schema(description = "影视目录")
@TableName(value ="movie_catalog")
@Data
@Builder
public class MovieCatalogDO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 路径
     */
    private String path;

    /**
     * 名称
     */
    private String name;

    /**
     * 文件大小
     */
    private Double totalSize;

    /**
     * 目录类型 如动漫电影
     */
    private String directory;

    /**
     * 最后扫描时间
     */
    private LocalDateTime lastScanTime;

    /**
     * 最后更新时间 递归子文件
     */
    private LocalDateTime lastModifyTime;

    /**
     * 存在状态 0正常 1过期
     */
    @TableLogic
    private MovieStatusEnum status;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}