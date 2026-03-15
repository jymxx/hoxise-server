package cn.hoxise.module.movie.controller.movie;

import cn.dev33.satoken.stp.StpUtil;
import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.common.security.operatelog.core.annotations.OperateLog;
import cn.hoxise.module.movie.controller.movie.dto.MovieScanUploadDTO;
import cn.hoxise.module.movie.controller.movie.dto.MovieUpdateDbDTO;
import cn.hoxise.module.movie.pojo.dto.BangumiSearchSubjectResponse;
import cn.hoxise.module.movie.service.MovieCatalogService;
import cn.hoxise.module.movie.service.MovieManageService;
import cn.hoxise.module.movie.service.bangumi.BangumiManageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 影视管理
 *
 * @author hoxise
 * @since 2026/01/14 14:56:07
 */
@Tag(name = "影视管理")
@RestController
@RequestMapping("/movie/manage")
@Validated
public class MovieManageController {

    @Resource private MovieManageService movieManageService;

    @Resource private MovieCatalogService movieCatalogService;

    @Resource private BangumiManageService bangumiManageService;

    @Operation(summary = "查询Bangumi")
    @GetMapping("/bangumi")
    public CommonResult<List<BangumiSearchSubjectResponse.Subject>> queryBangumi(String name) {
        StpUtil.checkLogin();
        return CommonResult.success(bangumiManageService.queryByNameFromBangumi(name));
    }

    @OperateLog
    @Operation(summary = "更新指定Bangumi信息")
    @PutMapping("/updateDb")
    public CommonResult<Boolean> updateDb(@Validated MovieUpdateDbDTO dto) {
        movieCatalogService.updateBangumi(dto);
        return CommonResult.ok();
    }

    @OperateLog
    @Operation(summary = "删除指定数据,逻辑删除")
    @DeleteMapping("/deleteCatalog/{catalogId}")
    public CommonResult<Boolean> delete(@PathVariable @NotNull Long catalogId) {
        movieCatalogService.removeAndCache(catalogId);
        return CommonResult.ok();
    }

    @OperateLog
    @Operation(summary = "扫描上传")
    @PostMapping("/scanUpload")
    public CommonResult<Boolean> scanUpload(@Validated MovieScanUploadDTO movieScanUploadDTO) {
        StpUtil.checkLogin();
        movieManageService.scanUpload(movieScanUploadDTO);
        return CommonResult.ok();
    }


    @OperateLog
    @Operation(summary = "自动匹配自己的数据")
    @PostMapping("/autoMatch")
    public CommonResult<Boolean> autoMatch() {
        movieManageService.autoMatch();
        return CommonResult.ok();
    }


}
