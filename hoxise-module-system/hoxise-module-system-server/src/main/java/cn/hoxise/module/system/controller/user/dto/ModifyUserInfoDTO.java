package cn.hoxise.module.system.controller.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 修改用户信息
 *
 * @author hoxise
 * @since 2026/4/2 上午10:06
 */
@Data
@AllArgsConstructor
public class ModifyUserInfoDTO {

    @Schema(description = "昵称")
    @Length(max = 20)
    private String nickName;
}
