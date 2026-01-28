package cn.hoxise.module.system.controller.dict;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.system.dal.entity.SystemDictDO;
import cn.hoxise.module.system.dal.entity.SystemDictDO;
import cn.hoxise.module.system.service.dict.SystemDictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统字典
 *
 * @author hoxise
 * @since 2026/01/14 06:10:10
 */
@Tag(name = "系统字典")
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
