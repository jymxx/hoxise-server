package cn.hoxise.module.system.service.user;

import cn.dev33.satoken.stp.StpUtil;
import cn.hoxise.common.base.enums.CommonStatusEnum;
import cn.hoxise.common.base.exception.ServiceException;
import cn.hoxise.common.file.core.client.FileStorageClientFactory;
import cn.hoxise.common.file.core.pojo.FileStorageDTO;
import cn.hoxise.module.system.controller.user.dto.ModifyUserInfoDTO;
import cn.hoxise.module.system.controller.user.vo.UserInfoVO;
import cn.hoxise.module.system.convert.SystemUserConvert;
import cn.hoxise.module.system.dal.entity.SystemRoleDO;
import cn.hoxise.module.system.dal.entity.SystemUserDO;
import cn.hoxise.module.system.dal.mapper.SystemUserMapper;
import cn.hoxise.module.system.enums.RoleEnum;
import cn.hoxise.module.system.pojo.constants.SystemConstants;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    @Resource private FileStorageClientFactory fileStorageClientFactory;

    @Value("${hoxise.defaultAvatar:}")
    private String defaultAvatar;

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

    @Override
    public SystemUserDO register(String phoneNumber){
        String name = "用户_" + UUID.randomUUID();//随机名称
        SystemUserDO userDO = SystemUserDO.builder()
                .userName(name)
                .phoneNumber(phoneNumber)
                .password(StrUtil.uuid())
                .nickName(name)
                .roleIds(Collections.singletonList(RoleEnum.USER.getCode().toString()))//默认普通角色
                .status(CommonStatusEnum.ENABLE.getStatus())
                .avatar(defaultAvatar)
                .build();
        baseMapper.insert(userDO);
        return userDO;
    }

    @Override
    public void modifyUserInfo(ModifyUserInfoDTO dto){
        long loginId = StpUtil.getLoginIdAsLong();
        baseMapper.update(Wrappers.lambdaUpdate(SystemUserDO.class)
                .eq(SystemUserDO::getUserId,loginId)
                .set(StrUtil.isNotBlank(dto.getNickName()),SystemUserDO::getNickName,dto.getNickName())
        );
    }

    @Override
    public String uploadAvatar(MultipartFile file) {
        // 校验文件类型（只允许图片）
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new ServiceException("只能上传图片文件");
        }

        // 校验文件大小（限制 10MB）
        long maxSize = 10 * 1024 * 1024;
        if (file.getSize() > maxSize) {
            throw new ServiceException("文件大小不能超过 10MB");
        }

        // 上传文件到头像目录
        FileStorageDTO fileStorageDTO = fileStorageClientFactory.getDefaultStorage().uploadFile(file, SystemConstants.USER_AVATAR_OSS_DIR);

        // 更新用户头像
        long loginId = StpUtil.getLoginIdAsLong();
        SystemUserDO one = getById(loginId);
        try{
            if (!one.getAvatar().equals(defaultAvatar) && StrUtil.isNotBlank(one.getAvatar())) {
                fileStorageClientFactory.getDefaultStorage().deleteFile(one.getAvatar());
            }
        }catch (Exception e){
            log.warn("用户头像删除失败.{%s},{%s}".formatted(one, e));
        }

        baseMapper.update(Wrappers.lambdaUpdate(SystemUserDO.class)
                .eq(SystemUserDO::getUserId, loginId)
                .set(SystemUserDO::getAvatar, fileStorageDTO.getObjectName()));

        return fileStorageClientFactory.getDefaultStorage().getAbsoluteUrl(fileStorageDTO.getObjectName());
    }

}




