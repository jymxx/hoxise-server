package cn.hoxise.module.system.controller.user;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hoxise.common.base.exception.ServiceException;
import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.system.controller.user.dto.ModifyUserInfoDTO;
import cn.hoxise.module.system.controller.user.vo.UserInfoVO;
import cn.hoxise.module.system.service.user.SystemUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户操作
 *
 * @author hoxise
 * @since 2026/01/14 06:10:45
 */
@Tag(name = "用户操作")
@RestController
@RequestMapping("/system/user")
@Validated
public class UserController {

    @Resource private SystemUserService systemUserService;

    @Operation(summary = "获取用户信息")
    @GetMapping("/info")
    public CommonResult<UserInfoVO> getUserInfo() {
        return CommonResult.success(systemUserService.getUserInfo());
    }

    @Operation(summary = "修改当前用户信息")
    @PutMapping("/modifyUserInfo")
    public CommonResult<Boolean> modifyUserInfo(@RequestBody ModifyUserInfoDTO userInfoVO){
        systemUserService.modifyUserInfo(userInfoVO);
        return CommonResult.ok();
    }

    @Operation(summary = "上传用户头像")
    @PutMapping("/uploadAvatar")
    public CommonResult<String> uploadAvatar(@NotNull MultipartFile file) {
        return CommonResult.success(systemUserService.uploadAvatar(file));
    }
}
