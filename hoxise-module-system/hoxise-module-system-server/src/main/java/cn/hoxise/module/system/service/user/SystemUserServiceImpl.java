package cn.hoxise.module.system.service.user;

import cn.dev33.satoken.stp.StpUtil;
import cn.hoxise.module.system.controller.user.dto.ModifyUserInfoDTO;
import cn.hoxise.module.system.controller.user.vo.UserInfoVO;
import cn.hoxise.module.system.convert.SystemUserConvert;
import cn.hoxise.module.system.dal.entity.SystemRoleDO;
import cn.hoxise.module.system.dal.entity.SystemUserDO;
import cn.hoxise.module.system.dal.mapper.SystemUserMapper;
import cn.hoxise.module.system.enums.RoleEnum;
import cn.hoxise.module.system.enums.UserStatusEnum;
import cn.hoxise.module.system.service.file.SystemFileService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

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
    @Resource private SystemFileService systemFileService;

    @Value("${hoxise.defaultAvatarFileId:}")
    private Long defaultAvatarFileId;

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
        // 设置头像URL
        if (systemUserDO.getAvatarFileId() != null) {
            convert.setAvatar(systemFileService.getObjectName(systemUserDO.getAvatarFileId()));
        }
        return convert;
    }

    @Override
    public SystemUserDO register(String phoneNumber){
        String name = "用户_" + UUID.randomUUID().toString().substring(0, 8);//随机名称
        SystemUserDO userDO = SystemUserDO.builder()
                .userName(name)
                .phoneNumber(phoneNumber)
                .password(StrUtil.uuid())
                .nickName(name)
                .roleIds(Collections.singletonList(RoleEnum.USER.getCode().toString()))//默认普通角色
                .status(UserStatusEnum.ENABLE.getStatus())
                .avatarFileId(defaultAvatarFileId)
                .build();
        baseMapper.insert(userDO);
        return userDO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyUserInfo(ModifyUserInfoDTO dto){
        long loginId = StpUtil.getLoginIdAsLong();

        // 处理头像更新
        if (dto.getAvatarFileId() != null) {
            // 删除旧头像（不删除默认头像）
            SystemUserDO user = getById(loginId);
            if (user.getAvatarFileId() != null && !user.getAvatarFileId().equals(defaultAvatarFileId)) {
                systemFileService.deleteFile(user.getAvatarFileId());
            }
            // 绑定新头像
            systemFileService.bindFile(dto.getAvatarFileId(), loginId);
        }

        // 更新用户信息
        baseMapper.update(Wrappers.lambdaUpdate(SystemUserDO.class)
                .eq(SystemUserDO::getUserId, loginId)
                .set(StrUtil.isNotBlank(dto.getNickName()), SystemUserDO::getNickName, dto.getNickName())
                .set(dto.getAvatarFileId() != null, SystemUserDO::getAvatarFileId, dto.getAvatarFileId())
        );
    }

}




