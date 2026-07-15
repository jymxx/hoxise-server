package cn.hoxise.module.system.api.file;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.system.service.file.SystemFileService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件 API 实现
 *
 * @author hoxise
 * @since 2026/07/08
 */
@RestController
public class FileApiImpl implements FileApi {

    @Resource
    private SystemFileService systemFileService;

    @Override
    public CommonResult<Void> deleteFile(String fileId) {
        systemFileService.deleteFile(fileId);
        return CommonResult.ok();
    }

    @Override
    public CommonResult<String> getAccessUrl(String fileId) {
        return CommonResult.success(systemFileService.getAccessUrl(fileId));
    }

    @Override
    public CommonResult<String> getDownloadUrl(String fileId) {
        return CommonResult.success(systemFileService.getDownloadUrl(fileId));
    }

    @Override
    public CommonResult<Void> bindFile(String fileId) {
        systemFileService.bindFile(fileId);
        return CommonResult.ok();
    }
}
