package cn.hoxise.system.biz.service.user;

import cn.dev33.satoken.stp.StpUtil;
import cn.hoxise.common.base.enums.CommonStatusEnum;
import cn.hoxise.system.biz.controller.user.vo.UserInfoVO;
import cn.hoxise.system.biz.convert.SystemUserConvert;
import cn.hoxise.system.biz.dal.entity.SystemRoleDO;
import cn.hoxise.system.biz.dal.entity.SystemUserDO;
import cn.hoxise.system.biz.dal.mapper.SystemUserMapper;
import cn.hoxise.system.enums.RoleEnum;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
* @author 永远的十七岁
* @description 用户类服务层
* @createDate 2023-08-27 00:15:59
*/
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUserDO>
    implements SystemUserService {

    @Resource private SystemRoleService systemRoleService;

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

    @Override
    public UserInfoVO getUserInfo(){
        long loginId = StpUtil.getLoginIdAsLong();
        SystemUserDO systemUserDO = this.getById(loginId);
        UserInfoVO convert = SystemUserConvert.INSTANCE.convert(systemUserDO);
        //设置角色信息
        List<SystemRoleDO> roles = systemRoleService.listByIds(systemUserDO.getRoleIds());
        convert.setRoles(roles.stream().map(SystemRoleDO::getRoleName).toList());
        return convert;
    }

    //注册
    @Override
    public SystemUserDO register(String phoneNumber){
        SystemUserDO userDO = SystemUserDO.builder()
                .userName(phoneNumber)
                .phoneNumber(phoneNumber)
                .password(StrUtil.uuid())
                .nickName(phoneNumber)
                .roleIds(Collections.singletonList(RoleEnum.USER.getCode().toString()))//默认普通角色
                .status(CommonStatusEnum.ENABLE)
                .build();
        //不知道是框架版本BUG还是怎么回事，自动插入失效，所以手动设置值
        userDO.setDeleted(false);

        this.save(userDO);
        return userDO;
    }

}




