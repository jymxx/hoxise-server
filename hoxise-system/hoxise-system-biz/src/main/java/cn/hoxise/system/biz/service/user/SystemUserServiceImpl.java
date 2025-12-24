package cn.hoxise.system.biz.service.user;

import cn.hoxise.system.biz.dal.entity.SystemUserDO;
import cn.hoxise.system.biz.dal.mapper.SystemUserMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author 永远的十七岁
* @description 用户类服务层
* @createDate 2023-08-27 00:15:59
*/
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUserDO>
    implements SystemUserService {

    @Override
    public SystemUserDO queryByUsername(String username){
        return this.getOne(Wrappers.lambdaQuery(SystemUserDO.class)
                .eq(SystemUserDO::getUserName,username));
    }

}




