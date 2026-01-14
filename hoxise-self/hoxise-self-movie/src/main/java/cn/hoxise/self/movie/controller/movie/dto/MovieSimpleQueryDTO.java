package cn.hoxise.self.movie.controller.movie.dto;

import cn.hoxise.common.base.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 简单查询结果DTO
 *
 * @author hoxise
 * @since 2026/01/14 14:54:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MovieSimpleQueryDTO extends PageParam {

    @Schema(description = "目录类型", example = "动漫/动漫电影")
    public String directory;

    @Schema(description = "名称关键字", example = "Clannad")
    public String keyword;
}
