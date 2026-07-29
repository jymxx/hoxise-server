package cn.hoxise.module.movie.controller.movie;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.common.security.operatelog.core.annotations.OperateLog;
import cn.hoxise.module.movie.controller.movie.vo.MovieExtraCheckVO;
import cn.hoxise.module.movie.controller.movie.dto.MovieCatalogExtraSaveDTO;
import cn.hoxise.module.movie.controller.movie.dto.MovieCatalogExtraUpdateDTO;
import cn.hoxise.module.movie.service.MovieCatalogExtraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hoxise
 * @since 2026/4/6 下午5:21
 */
@Tag(name = "影视目录-拓展信息控制类")
@RestController
@RequestMapping("/movie/catalogExtra")
@Validated
public class MovieCatalogExtraController {

    @Resource
    private MovieCatalogExtraService movieCatalogExtraService;

    @SaCheckRole("manager")
    @Operation(summary = "新增扩展信息")
    @PostMapping("/save")
    public CommonResult<Void> save(@Validated @RequestBody MovieCatalogExtraSaveDTO dto) {
        movieCatalogExtraService.saveExtra(dto);
        return CommonResult.ok();
    }

    @SaCheckRole("manager")
    @Operation(summary = "更新扩展信息")
    @PutMapping("/update")
    public CommonResult<Void> update(@Validated @RequestBody MovieCatalogExtraUpdateDTO dto) {
        movieCatalogExtraService.updateExtra(dto);
        return CommonResult.ok();
    }

    @SaCheckRole("manager")
    @Operation(summary = "删除扩展信息")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Void> delete(@PathVariable @NotNull Long id) {
        movieCatalogExtraService.deleteExtra(id);
        return CommonResult.ok();
    }

    @Operation(summary = "检查是否存在可获取的信息")
    @GetMapping("/hasInfo")
    @SaIgnore
    public CommonResult<List<MovieExtraCheckVO>> hasInfo(@NotNull Long catalogId) {
        return CommonResult.success(movieCatalogExtraService.hasInfo(catalogId));
    }

    @OperateLog // 记录日志
    @Operation(summary = "查询实际资源地址")
    @GetMapping("/resourceUrl")
    public CommonResult<String> getResourceUrl(@NotNull Long extraId, String secret) {
        return CommonResult.success(movieCatalogExtraService.getResourceUrl(extraId, secret));
    }
}
