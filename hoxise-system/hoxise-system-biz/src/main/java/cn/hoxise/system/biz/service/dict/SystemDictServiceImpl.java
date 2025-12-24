package cn.hoxise.system.biz.service.dict;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hoxise.system.biz.dal.entity.SystemDictDO;
import cn.hoxise.system.biz.dal.mapper.SystemDictMapper;
import org.springframework.stereotype.Service;

/**
* @author Hoxise
* @description 针对表【system_dict】的数据库操作Service实现
* @createDate 2025-12-24 16:17:36
*/
@Service
public class SystemDictServiceImpl extends ServiceImpl<SystemDictMapper, SystemDictDO>
    implements SystemDictService {

    @Override
    public SystemDictDO getByKey(String key) {
        if (StrUtil.isBlank(key)){
            return null;
        }
        return this.getOne(Wrappers.lambdaQuery(SystemDictDO.class).eq(SystemDictDO::getDictKey, key));
    }
}




