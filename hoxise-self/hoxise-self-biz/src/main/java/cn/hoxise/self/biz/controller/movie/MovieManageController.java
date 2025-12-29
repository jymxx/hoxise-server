package cn.hoxise.self.biz.controller.movie;

import cn.dev33.satoken.stp.StpUtil;
import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.self.biz.pojo.dto.BangumiSearchSubjectResponse;
import cn.hoxise.self.biz.service.movie.bangumi.MovieBangumiManageService;
import cn.hoxise.system.enums.RoleEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author hoxise
 * @Description: 影视管理
 * @Date 2025-12-29 上午6:36
 */
@Tag(name = "影视管理")
@RestController
@RequestMapping("/movie/manage")
public class MovieManageController {


    @Resource private MovieBangumiManageService movieBangumiManageService;

    @Operation(summary = "扫描Bangumi")
    @GetMapping("/bangumi")
    public CommonResult<List<BangumiSearchSubjectResponse.Subject>> queryBangumi(String name) {
        StpUtil.checkRole(RoleEnum.manager.getName());
        return CommonResult.success(movieBangumiManageService.queryByNameFromBangumi(name));
    }

    @Operation(summary = "更新指定Bangumi信息")
    @PutMapping("/bangumi")
    public CommonResult<Boolean> updateBangumi(Long catalogid, Long bangumiId) {
        StpUtil.checkRole(RoleEnum.manager.getName());//管理员角色
        movieBangumiManageService.updateBangumi(catalogid, bangumiId);
        return CommonResult.ok();
    }





}
