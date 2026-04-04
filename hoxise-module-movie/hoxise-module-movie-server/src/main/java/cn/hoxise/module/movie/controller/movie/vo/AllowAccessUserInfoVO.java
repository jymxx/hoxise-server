package cn.hoxise.module.movie.controller.movie.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 允许访问用户数据
 *
 * @author hoxise
 * @since 2026/4/3 上午9:31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllowAccessUserInfoVO {

    @Schema(name = "用户id")
    private Long userId;

    @Schema(name = "昵称")
    private String nickName;
}
