package cn.hoxise.self.movie.dal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * @TableName movie_contents
 */
@Schema(description = "影视内容(子集)")
@TableName(value ="movie_contents")
@Data
public class MovieContentsDO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 父级id
     */
    private Long catalogid;

    /**
     * 相对路径
     */
    private String relativePath;

    /**
     * 文件名
     */
    private String name;

    /**
     * 季度、OVA等
     */
    private String classify;

    /**
     * 文件大小
     */
    private Double size;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}