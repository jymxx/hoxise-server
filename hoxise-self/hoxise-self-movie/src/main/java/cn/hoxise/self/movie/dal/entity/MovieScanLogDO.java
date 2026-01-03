package cn.hoxise.self.movie.dal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * @TableName movie_scan_log
 */
@Schema(description = "扫描日志")
@TableName(value ="movie_scan_log")
@Data
public class MovieScanLogDO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 扫描开始时间
     */
    private LocalDateTime startScanTime;

    /**
     * 扫描结束时间
     */
    private LocalDateTime endScanTime;

    /**
     * 报错日志
     */
    private String log;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}