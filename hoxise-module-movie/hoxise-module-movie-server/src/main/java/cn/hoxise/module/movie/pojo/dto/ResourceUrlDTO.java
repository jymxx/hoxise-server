package cn.hoxise.module.movie.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 资源地址 DTO
 *
 * @author hoxise
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceUrlDTO {

    /**
     * 名称
     */
    private String name;

    /**
     * 链接
     */
    private String url;

    /**
     * 备注
     */
    private String remark;
}