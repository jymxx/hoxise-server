package cn.hoxise.module.system.controller.access;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hoxise.common.base.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务访问
 *
 * @author hoxise
 * @since 2026/4/6 下午5:54
 */
@Tag(name = "访问访问")
@RestController
@RequestMapping("/system/access")
public class AccessController {

    @Operation(summary = "验证服务状态")
    @GetMapping("/validate")
    @SaIgnore
    public CommonResult<Boolean> validate(){
        return CommonResult.success(true);
    }
}
