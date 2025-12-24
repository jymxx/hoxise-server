package cn.hoxise.self.biz.controller.movie;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.common.base.pojo.PageResult;
import cn.hoxise.self.biz.controller.movie.dto.MovieSimpleQueryDTO;
import cn.hoxise.self.biz.controller.movie.vo.MovieSimpleVO;
import cn.hoxise.self.biz.controller.movie.vo.MovieStatVO;
import cn.hoxise.self.biz.service.movie.MovieCatalogService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author hoxise
 * @Description: 影视目录控制类
 * @Date 2025-12-22 上午7:41
 */
@RestController
@RequestMapping("/movie/catalog")
public class MovieCatalogController {

    @Resource private MovieCatalogService movieCatalogService;

    @Operation(summary = "随机查询数据(5条)")
    @GetMapping("/randomQuery")
    public CommonResult<List<MovieSimpleVO>> randomQuery(Integer limit){
        return CommonResult.success(movieCatalogService.randomQuery(limit));
    }

    @Operation(summary = "获取最近更新的数据")
    @GetMapping("/lastUpdate")
    public CommonResult<List<MovieSimpleVO>> lastUpdate(){
        return CommonResult.success(movieCatalogService.LastUpdate());
    }

    @Operation(summary = "获取影视库数据")
    @GetMapping("/library")
    public CommonResult<PageResult<MovieSimpleVO>> libraryDBCache(MovieSimpleQueryDTO queryDTO) {
        return CommonResult.success(movieCatalogService.libraryDBCache(queryDTO));
    }

    @Operation(summary = "获取影视目录列表")
    @GetMapping("/pageSimple")
    public CommonResult<PageResult<MovieSimpleVO>> pageSimple(MovieSimpleQueryDTO queryDTO){
        return CommonResult.success(movieCatalogService.listPageContainDB(queryDTO));
    }

    @Operation(summary = "获取影视统计数据")
    @GetMapping("/movieStat")
    public CommonResult<MovieStatVO> movieStat(){
        return CommonResult.success(movieCatalogService.statCount());
    }

}
