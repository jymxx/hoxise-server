package cn.hoxise.module.movie.controller.movie;

import cn.dev33.satoken.stp.StpUtil;
import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.common.security.operatelog.core.annotations.OperateLog;
import cn.hoxise.module.movie.pojo.dto.BangumiSearchSubjectResponse;
import cn.hoxise.module.movie.service.movie.MovieCatalogService;
import cn.hoxise.module.movie.service.movie.bangumi.BangumiManageService;
import cn.hoxise.module.system.enums.RoleEnum;
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

    @Resource private MovieCatalogService movieCatalogService;

    @Resource private BangumiManageService bangumiManageService;

    @Operation(summary = "扫描Bangumi")
    @GetMapping("/bangumi")
    public CommonResult<List<BangumiSearchSubjectResponse.Subject>> queryBangumi(String name) {
        StpUtil.checkRole(RoleEnum.manager.getName());
        return CommonResult.success(bangumiManageService.queryByNameFromBangumi(name));
    }

    @OperateLog
    @Operation(summary = "更新指定Bangumi信息")
    @PutMapping("/updateDb")
    public CommonResult<Boolean> updateDb(Long catalogid, Long bangumiId) {
        StpUtil.checkRole(RoleEnum.manager.getName());//管理员角色
        bangumiManageService.updateBangumi(catalogid, bangumiId);
        return CommonResult.ok();
    }

    @OperateLog
    @Operation(summary = "删除指定数据,逻辑删除")
    @DeleteMapping("/deleteCatalog/{catalogid}")
    public CommonResult<Boolean> delete(@PathVariable @NotNull Long catalogid) {
        StpUtil.checkRole(RoleEnum.manager.getName());
        movieCatalogService.removeAndCache(catalogid);
        return CommonResult.ok();
    }




}
