package cn.hoxise.module.system.controller.file;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.system.controller.file.vo.PresignedUploadVO;
import cn.hoxise.module.system.enums.FileOssDirEnum;
import cn.hoxise.module.system.service.file.SystemFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传
 *
 * @author hoxise
 * @since 2026/04/12
 */
@Tag(name = "文件上传")
@RestController
@RequestMapping("/system/file")
@Validated
public class SystemFileController {

    @Resource
    private SystemFileService systemFileService;

    @SaCheckRole("manager")
    @Operation(summary = "上传文件")
    @PostMapping("/upload")
    public CommonResult<String> upload(@NotNull MultipartFile file, @NotNull @RequestParam FileOssDirEnum bizType) {
        return CommonResult.success(systemFileService.uploadFile(file, bizType));
    }

    @Operation(summary = "获取文件访问URL")
    @GetMapping("/getAccessUrl")
    public CommonResult<String> getAccessUrl(@NotBlank String fileId) {
        return CommonResult.success(systemFileService.getAccessUrl(fileId));
    }

    @SaCheckRole("manager")
    @Operation(summary = "生成预签名上传 URL（前端直传 OSS）")
    @PostMapping("/generatePresignedUrl")
    public CommonResult<PresignedUploadVO> generatePresignedUrl(@NotBlank @RequestParam String fileName,
                                                                 @NotNull @RequestParam FileOssDirEnum bizType) {
        return CommonResult.success(systemFileService.generatePresignedUrl(fileName, bizType));
    }
}