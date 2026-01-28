package cn.hoxise.module.system.service.dict;

import cn.hoxise.module.system.dal.entity.SystemDictDO;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hoxise.module.system.dal.entity.SystemDictDO;
import cn.hoxise.module.system.dal.mapper.SystemDictMapper;
import org.springframework.stereotype.Service;

/**
 * 字典
 *
 * @author hoxise
 * @since 2026/1/14 上午5:56
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




