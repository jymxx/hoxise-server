package cn.hoxise.system.biz.service.dict;

import cn.hoxise.system.biz.dal.entity.SystemDictDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Hoxise
* @description 针对表【system_dict】的数据库操作Service
* @createDate 2025-12-24 16:17:36
*/
public interface SystemDictService extends IService<SystemDictDO> {

    SystemDictDO getByKey(String key);
}
