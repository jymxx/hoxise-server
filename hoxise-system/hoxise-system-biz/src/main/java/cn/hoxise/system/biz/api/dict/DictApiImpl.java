package cn.hoxise.system.biz.api.dict;

import cn.hoxise.system.api.dict.DictApi;
import cn.hoxise.system.biz.dal.entity.SystemDictDO;
import cn.hoxise.system.biz.service.dict.SystemDictService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author hoxise
 * @Description:
 * @Date 2025-12-24 下午5:09
 */
//字典实现类
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
