package cn.hoxise.system.biz.controller.dict;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.system.biz.dal.entity.SystemDictDO;
import cn.hoxise.system.biz.service.dict.SystemDictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author hoxise
 * @Description: 系统简单配置控制类
 * @Date 2025-12-24 下午4:21
 */
@Tag(name = "系统简单配置获取")
@RestController
@RequestMapping("/system/dict")
public class DictController {

    @Resource
    private SystemDictService systemDictService;

    @Operation(summary = "通过key获取配置")
    @GetMapping("/getByKey")
    public CommonResult<SystemDictDO> getByKey(String key){
        return CommonResult.success(systemDictService.getByKey(key));
    }

}
