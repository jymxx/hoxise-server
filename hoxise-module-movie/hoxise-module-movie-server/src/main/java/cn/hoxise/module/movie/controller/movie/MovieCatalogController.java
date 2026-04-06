package cn.hoxise.module.movie.controller.movie;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.common.base.pojo.PageResult;
import cn.hoxise.module.movie.controller.movie.dto.MovieLibraryQueryDTO;
import cn.hoxise.module.movie.controller.movie.dto.MovieSimpleQueryDTO;
import cn.hoxise.module.movie.controller.movie.vo.MovieSimpleVO;
import cn.hoxise.module.movie.controller.movie.vo.MovieStatVO;
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
 * 影视目录控制类
 *
 * @author hoxise
 * @since 2026/01/14 14:55:42
 */
@Tag(name = "影视目录控制类")
@RestController
@RequestMapping("/movie/catalog")
@Validated
public class MovieCatalogController {

    @Resource private MovieCatalogService movieCatalogService;

    @Resource private MovieFavoriteService movieFavoriteService;

    @Operation(summary = "随机查询数据")
    @GetMapping("/{userid}/randomQuery")
    @SaIgnore
    public CommonResult<List<MovieSimpleVO>> randomQuery(Integer limit,@NotNull @PathVariable Long userid){
        List<MovieSimpleVO> list = movieCatalogService.randomQuery(limit, userid);
        movieCatalogService.fillFavoriteInfo(list); // 填充收藏信息
        movieCatalogService.handleNoLogin(list); // 处理未登录状态
        return CommonResult.success(list);
    }

    @Operation(summary = "获取最近更新的数据")
    @GetMapping("/{userid}/lastUpdate")
    @SaIgnore
    public CommonResult<List<MovieSimpleVO>> lastUpdate(@NotNull @PathVariable Long userid){
        List<MovieSimpleVO> list = movieCatalogService.lastUpdate(userid);
        movieCatalogService.fillFavoriteInfo(list); // 填充收藏信息
        movieCatalogService.handleNoLogin(list); // 处理未登录状态
        return CommonResult.success(list);
    }

    @Operation(summary = "获取影视库数据")
    @GetMapping("/{userid}/library")
    @SaIgnore
    public CommonResult<PageResult<MovieSimpleVO>> libraryDdCache(@Validated MovieLibraryQueryDTO queryDTO, @NotNull @PathVariable Long userid) {
        queryDTO.setUserid(userid);
        PageResult<MovieSimpleVO> result = movieCatalogService.libraryDbCache(queryDTO);
        movieCatalogService.fillFavoriteInfo(result.getList()); // 填充收藏信息 放在外面防缓存
        movieCatalogService.handleNoLogin(result.getList()); // 处理未登录状态
        return CommonResult.success(result);
    }

    @Operation(summary = "获取影视目录列表")
    @GetMapping("/{userid}/pageSimple")
    @SaIgnore
    public CommonResult<PageResult<MovieSimpleVO>> pageSimple(@Validated MovieSimpleQueryDTO queryDTO,@NotNull @PathVariable Long userid){
        queryDTO.setUserid(userid);
        PageResult<MovieSimpleVO> result = movieCatalogService.listPageContainDb(queryDTO);
        movieCatalogService.fillFavoriteInfo(result.getList()); // 填充收藏信息
        movieCatalogService.handleNoLogin(result.getList()); // 处理未登录状态
        return CommonResult.success(result);
    }

    @Operation(summary = "获取影视统计数据")
    @GetMapping("/{userid}/movieStat")
    @SaIgnore
    public CommonResult<MovieStatVO> movieStat(@NotNull @PathVariable Long userid){
        return CommonResult.success(movieCatalogService.statCount(userid));
    }

    @Operation(summary = "获取收藏列表（含 DB 数据）")
    @GetMapping("/listFavorite")
    public CommonResult<List<MovieSimpleVO>> getFavoriteList() {
        return CommonResult.success(movieCatalogService.getFavoriteList());
    }




}
