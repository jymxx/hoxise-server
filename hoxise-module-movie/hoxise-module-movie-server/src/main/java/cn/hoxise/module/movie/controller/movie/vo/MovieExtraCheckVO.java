package cn.hoxise.module.movie.controller.movie.vo;

import cn.hoxise.module.movie.enums.MovieResourceTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 拓展信息检查VO
 *
 * @author hoxise
 * @since 2026/4/9 下午8:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieExtraCheckVO {

    @Schema(description = "主键 ID")
    private Long id;

    @Schema(description = "关联目录 ID")
    private Long catalogId;

    @Schema(description = "资源类型")
    private MovieResourceTypeEnum resourceType;

    @Schema(description = "显示名称")
    private String showName;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "是否设置了密钥")
    private boolean hasSecret;


}
