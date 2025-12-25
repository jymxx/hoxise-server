package cn.hoxise.system.biz.service.user;

import cn.hoxise.common.base.enums.CommonStatusEnum;
import cn.hoxise.system.biz.dal.entity.SystemUserDO;
import cn.hoxise.system.biz.dal.mapper.SystemUserMapper;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collections;

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

    @Override
    public SystemUserDO queryByPhoneNumber(String phoneNumber){
        return this.getOne(Wrappers.lambdaQuery(SystemUserDO.class)
                .eq(SystemUserDO::getPhoneNumber,phoneNumber));
    }


    //注册
    @Override
    public SystemUserDO register(String phoneNumber){
        SystemUserDO userDO = SystemUserDO.builder()
                .userName(phoneNumber)
                .phoneNumber(phoneNumber)
                .password(StrUtil.uuid())
                .nickName(phoneNumber)
                .roleIds(Collections.singletonList("2"))//先不配置角色 目前还没用到
                .status(CommonStatusEnum.ENABLE)
                .build();

        //不知道是框架版本BUG还是怎么回事，不手动设置值会有问题
        userDO.setDeleted(false);

        this.save(userDO);
        return userDO;
    }

}




