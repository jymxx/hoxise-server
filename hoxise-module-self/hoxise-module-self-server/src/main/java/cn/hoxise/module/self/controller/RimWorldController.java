package cn.hoxise.module.self.controller;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.self.controller.dto.RwTransAcceptFileDTO;
import cn.hoxise.module.self.controller.dto.RwTransPreCheckDTO;
import cn.hoxise.module.self.controller.dto.RwTransStartDTO;
import cn.hoxise.module.self.pojo.bo.RwSubTransBO;
import cn.hoxise.module.self.service.RwTransService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 环世界工具
 *
 * @author hoxise
 * @since 2026/2/3 上午9:37
 */
@Tag(name = "RimWorld控制类")
@RestController
@RequestMapping("/self/rw")
public class RimWorldController {

    @Resource private RwTransService rwTransService;

    @Operation(summary = "翻译-预检")
    @PostMapping("/pre-check")
    public CommonResult<String> preCheck(@Validated RwTransPreCheckDTO dto){
        return CommonResult.success(rwTransService.preCheck(dto));
    }

    @Operation(summary = "翻译-上传文件")
    @PostMapping("/accept-file")
    public CommonResult<String> acceptFile(@Validated RwTransAcceptFileDTO dto){
        rwTransService.acceptFile(dto);
        return CommonResult.ok();
    }

    @Operation(summary = "翻译-开始翻译")
    @PostMapping("/start-trans")
    public CommonResult<String> startTrans(@Validated RwTransStartDTO dto){
        rwTransService.startTrans(dto);
        return CommonResult.ok();
    }

    @Operation(summary = "翻译-获取翻译列表")
    @GetMapping("/get-trans-list")
    public CommonResult<List<RwSubTransBO>> getTransStatus(String preCheckId){
        return CommonResult.success(rwTransService.getTransList(preCheckId));
    }

}
