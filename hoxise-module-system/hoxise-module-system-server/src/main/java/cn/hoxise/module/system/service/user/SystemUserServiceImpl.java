package cn.hoxise.module.system.service.user;

import cn.hoxise.common.base.enums.CommonStatusEnum;
import cn.hoxise.common.security.satoken.uitls.SaTokenUtil;
import cn.hoxise.module.system.controller.user.vo.UserInfoVO;
import cn.hoxise.module.system.convert.SystemUserConvert;
import cn.hoxise.module.system.dal.entity.SystemRoleDO;
import cn.hoxise.module.system.dal.entity.SystemUserDO;
import cn.hoxise.module.system.controller.user.vo.UserInfoVO;
import cn.hoxise.module.system.convert.SystemUserConvert;
import cn.hoxise.module.system.dal.entity.SystemRoleDO;
import cn.hoxise.module.system.dal.entity.SystemUserDO;
import cn.hoxise.module.system.dal.mapper.SystemUserMapper;
import cn.hoxise.module.system.enums.RoleEnum;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * SystemUserServiceImpl
 *
 * @author 永远的十七岁
 * @since 2026/01/14 05:57:03
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
        long loginId = SaTokenUtil.getLoginIdAsLong();
        SystemUserDO systemUserDO = this.getById(loginId);
        UserInfoVO convert = SystemUserConvert.INSTANCE.convert(systemUserDO);
        //设置角色信息
        List<SystemRoleDO> roles = systemRoleService.listByIds(systemUserDO.getRoleIds());
        convert.setRoles(roles.stream().map(SystemRoleDO::getRoleName).toList());
        return convert;
    }

    @Override
    public SystemUserDO register(String phoneNumber){
        SystemUserDO userDO = SystemUserDO.builder()
                .userName(phoneNumber)
                .phoneNumber(phoneNumber)
                .password(StrUtil.uuid())
                .nickName(phoneNumber)
                .roleIds(Collections.singletonList(RoleEnum.USER.getCode().toString()))//默认普通角色
                .status(CommonStatusEnum.ENABLE.getStatus())
                .build();
        baseMapper.insert(userDO);
        return userDO;
    }

}




