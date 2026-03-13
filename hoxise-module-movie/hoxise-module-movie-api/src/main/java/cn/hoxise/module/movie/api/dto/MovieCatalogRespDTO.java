package cn.hoxise.module.movie.api.dto;

import cn.hoxise.module.movie.enums.movie.MovieTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * MovieCatalogRespDTO
 *
 * @author hoxise
 * @since 2026/03/12 21:39:28
 */
@Data
public class MovieCatalogRespDTO {

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
     * 目录名称
     */
    private MovieTypeEnum directory;

    /**
     * 用户id 属于哪个用户的数据
     */
    private Long userid;

    /**
     * 关联bangumi数据id
     */
    private Long bangumiId;

    /**
     * 数据创建时间
     */
    private LocalDateTime createTime;

    /**
     * 匹配时间
     */
    private LocalDateTime matchingTime;
}
