package cn.hoxise.self.movie.controller.movie;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.common.log.core.annotations.OperateLog;
import cn.hoxise.common.security.uitls.SaTokenUtil;
import cn.hoxise.self.movie.pojo.dto.BangumiSearchSubjectResponse;
import cn.hoxise.self.movie.service.movie.MovieCatalogService;
import cn.hoxise.self.movie.service.movie.bangumi.MovieBangumiManageService;
import cn.hoxise.system.enums.RoleEnum;
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

    @Resource private MovieBangumiManageService movieBangumiManageService;

    @Operation(summary = "扫描Bangumi")
    @GetMapping("/bangumi")
    public CommonResult<List<BangumiSearchSubjectResponse.Subject>> queryBangumi(String name) {
        SaTokenUtil.checkRole(RoleEnum.manager.getName());
        return CommonResult.success(movieBangumiManageService.queryByNameFromBangumi(name));
    }

    @OperateLog
    @Operation(summary = "更新指定Bangumi信息")
    @PutMapping("/updateDb")
    public CommonResult<Boolean> updateDb(Long catalogid, Long bangumiId) {
        SaTokenUtil.checkRole(RoleEnum.manager.getName());//管理员角色
        movieBangumiManageService.updateBangumi(catalogid, bangumiId);
        return CommonResult.ok();
    }

    @OperateLog
    @Operation(summary = "删除指定数据,逻辑删除")
    @DeleteMapping("/deleteCatalog/{catalogid}")
    public CommonResult<Boolean> delete(@PathVariable @NotNull Long catalogid) {
        SaTokenUtil.checkRole(RoleEnum.manager.getName());
        movieCatalogService.removeById(catalogid);
        return CommonResult.ok();
    }




}
