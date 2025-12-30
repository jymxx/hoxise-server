package cn.hoxise.system.biz.controller.user;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.system.biz.controller.user.vo.UserInfoVO;
import cn.hoxise.system.biz.service.user.SystemUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: hoxise
 * @Description: 用户相关操作
 * @Date: 2023/8/27 0:21
 */
@Tag(name = "用户操作")
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Resource private SystemUserService systemUserService;

    @Operation(summary = "获取用户信息")
    @GetMapping("/info")
    public CommonResult<UserInfoVO> getUserInfo(){
        return CommonResult.success(systemUserService.getUserInfo());
    }
}
