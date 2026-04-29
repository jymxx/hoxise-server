package cn.hoxise.module.movie.controller.movie;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.common.security.operatelog.core.annotations.OperateLog;
import cn.hoxise.module.movie.controller.movie.vo.MovieExtraCheckVO;
import cn.hoxise.module.movie.dal.entity.MovieCatalogExtraDO;
import cn.hoxise.module.movie.pojo.dto.PlayUrlDTO;
import cn.hoxise.module.movie.pojo.dto.ResourceUrlDTO;
import cn.hoxise.module.movie.service.MovieCatalogExtraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Resource private MovieCatalogExtraService movieCatalogExtraService;

    @OperateLog
    @Operation(summary = "检查是否存在可获取的信息")
    @GetMapping("/hasInfo")
    public CommonResult<MovieExtraCheckVO> hasInfo(@NotNull Long catalogId){
        return CommonResult.success(movieCatalogExtraService.hasInfo(catalogId));
    }

    @OperateLog // 显式记录get请求的操作日志
    @Operation(summary = "获取播放地址")
    @GetMapping("/getPlayUrl")
    public CommonResult<PlayUrlDTO> getPlayUrl(@NotNull Long catalogId, String keySecret) {
        return CommonResult.success(movieCatalogExtraService.getExtraInfo(catalogId, keySecret, MovieCatalogExtraDO::getPlayUrl));
    }

    @OperateLog // 显式记录get请求的操作日志
    @Operation(summary = "获取资源地址")
    @GetMapping("/getResourceUrl")
    public CommonResult<List<ResourceUrlDTO>> getResourceUrl(@NotNull Long catalogId, String keySecret) {
        return CommonResult.success(movieCatalogExtraService.getExtraInfo(catalogId, keySecret, MovieCatalogExtraDO::getResourceUrl));
    }
}
