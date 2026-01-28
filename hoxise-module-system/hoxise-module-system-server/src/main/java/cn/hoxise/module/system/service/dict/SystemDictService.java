package cn.hoxise.module.system.service.dict;

import cn.hoxise.module.system.dal.entity.SystemDictDO;
import cn.hoxise.module.system.dal.entity.SystemDictDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * SystemDictService
 *
 * @author hoxise
 * @since 2026/1/14 上午5:56
 */
public interface SystemDictService extends IService<SystemDictDO> {

    /**
     * getByKey 获取字典根据key
     *
     * @param key 字典key
     * @return 系统字典
     * @author hoxise
     * @since 2026/01/14 06:03:32
     */
    SystemDictDO getByKey(String key);
}
