package cn.hoxise.module.movie.controller.movie;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.movie.service.MovieDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 影视数据控制类
 *
 * @author hoxise
 * @since 2026/4/2 下午11:43
 */
@Tag(name = "影视数据控制类")
@RestController
@RequestMapping("/movie/data")
@Validated
public class MovieDataController {

    @Resource
    private MovieDataService movieDataService;

    @Operation(summary = "判断用户数据是否可以访问")
    @GetMapping("/allowAccess")
    public CommonResult<Boolean> allowAccess(@NotNull Long userid){
        return CommonResult.success(movieDataService.allowAccess(userid));
    }

}
