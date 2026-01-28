package cn.hoxise.module.system.api.dict;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.system.dal.entity.SystemDictDO;
import cn.hoxise.module.system.service.dict.SystemDictService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

/**
 * DictApiImpl
 *
 * @author hoxise
 * @since 2026/01/14 06:08:40
 */
@RestController
public class DictApiImpl implements DictApi {

    @Resource private SystemDictService systemDictService;

    @Override
    public CommonResult<String> getByKey(String key) {
        SystemDictDO dict = systemDictService.getByKey(key);
        return CommonResult.success(dict==null? null : dict.getDictValue());
    }
}
