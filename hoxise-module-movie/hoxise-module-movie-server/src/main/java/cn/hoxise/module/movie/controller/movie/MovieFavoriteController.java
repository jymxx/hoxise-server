package cn.hoxise.module.movie.controller.movie;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.movie.controller.movie.vo.MovieSimpleVO;
import cn.hoxise.module.movie.service.MovieCatalogService;
import cn.hoxise.module.movie.service.MovieFavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 动漫收藏控制类
 *
 * @author hoxise
 * @since 2026/04/06
 */
@Tag(name = "动漫收藏控制类")
@RestController
@RequestMapping("/movie/favorite")
@Validated
public class MovieFavoriteController {

    @Resource
    private MovieFavoriteService movieFavoriteService;

    @Operation(summary = "收藏")
    @PostMapping("/add")
    public CommonResult<Boolean> add(@NotNull Long catalogId) {
        movieFavoriteService.favorite(catalogId);
        return CommonResult.success(true);
    }

    @Operation(summary = "取消收藏")
    @DeleteMapping("/cancel/{catalogId}")
    public CommonResult<Boolean> remove(@PathVariable @NotNull Long catalogId) {
        movieFavoriteService.unfavorite(catalogId);
        return CommonResult.success(true);
    }

    @Operation(summary = "检查是否已收藏")
    @GetMapping("/check")
    public CommonResult<Boolean> check(@RequestParam @NotNull Long catalogId) {
        return CommonResult.success(movieFavoriteService.isFavorited(catalogId));
    }

    @Operation(summary = "获取用户收藏的目录 ID 列表")
    @GetMapping("/catalogIds")
    public CommonResult<List<Long>> getFavoriteCatalogIds() {
        return CommonResult.success(movieFavoriteService.getFavoriteCatalogIds());
    }
}
