package cn.hoxise.system.biz.api.dict;

import cn.hoxise.system.api.dict.DictApi;
import cn.hoxise.system.biz.dal.entity.SystemDictDO;
import cn.hoxise.system.biz.service.dict.SystemDictService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * DictApiImpl
 *
 * @author hoxise
 * @since 2026/01/14 06:08:40
 */
@Service
public class DictApiImpl implements DictApi {

    @Resource private SystemDictService systemDictService;

    @Override
    public String getByKey(String key) {
        SystemDictDO dict = systemDictService.getByKey(key);
        if (dict==null){
            return null;
        }
        return dict.getDictValue();
    }
}
