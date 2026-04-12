package cn.hoxise.module.movie.controller.movie.vo;

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

    @Schema(description = "是否存在播放地址")
    private boolean hasPlayUrl;

    @Schema(description = "是否存在资源地址")
    private boolean hasResourceUrl;

    @Schema(description = "是否存在密钥")
    private boolean hasSecret;

}
