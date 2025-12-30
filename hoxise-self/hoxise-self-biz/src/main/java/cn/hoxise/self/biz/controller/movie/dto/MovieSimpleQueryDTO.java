package cn.hoxise.self.biz.controller.movie.dto;

import cn.hoxise.common.base.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author hoxise
 * @Description: 简单查询DTO
 * @Date 2025-12-22 下午3:49
 */
@Data
public class MovieSimpleQueryDTO extends PageParam {

    @Schema(description = "目录类型", example = "动漫/动漫电影")
    public String directory;

    @Schema(description = "名称关键字", example = "Clannad")
    public String keyword;
}
