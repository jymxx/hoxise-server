package cn.hoxise.module.system.api.file;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.system.enums.RpcConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 文件 API
 *
 * @author hoxise
 * @since 2026/07/08
 */
@FeignClient(name = RpcConstants.NAME, contextId = "FileApi")
@Tag(name = "RPC 文件接口")
public interface FileApi {

    String PREFIX = RpcConstants.API_PREFIX + "/file";

    @Operation(summary = "删除文件")
    @DeleteMapping(PREFIX + "/delete/{fileId}")
    @Parameter(name = "fileId", description = "文件 ID", required = true)
    CommonResult<Void> deleteFile(@PathVariable String fileId);

    @Operation(summary = "获取文件预览/访问 URL")
    @GetMapping(PREFIX + "/getAccessUrl")
    @Parameter(name = "fileId", description = "文件 ID", required = true)
    CommonResult<String> getAccessUrl(@RequestParam("fileId") String fileId);

    @Operation(summary = "获取文件下载 URL")
    @GetMapping(PREFIX + "/getDownloadUrl")
    @Parameter(name = "fileId", description = "文件 ID", required = true)
    CommonResult<String> getDownloadUrl(@RequestParam("fileId") String fileId);

    @Operation(summary = "绑定文件（临时目录迁移到正式目录）")
    @PutMapping(PREFIX + "/bind")
    @Parameter(name = "fileId", description = "文件 ID", required = true)
    CommonResult<Void> bindFile(@RequestParam("fileId") String fileId);
}
