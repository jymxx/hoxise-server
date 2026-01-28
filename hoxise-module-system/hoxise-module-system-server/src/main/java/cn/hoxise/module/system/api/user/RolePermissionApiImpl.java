package cn.hoxise.module.system.api.user;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.system.dal.entity.SystemRoleDO;
import cn.hoxise.module.system.dal.entity.SystemUserDO;
import cn.hoxise.module.system.service.user.SystemRoleService;
import cn.hoxise.module.system.service.user.SystemUserService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * RolePermissionApiImpl
 *
 * @author hoxise
 * @since 2026/01/14 06:09:11
 */
@RestController
public class RolePermissionApiImpl implements RolePermissionApi {

    @Resource private SystemUserService systemUserService;
    @Resource private SystemRoleService systemRoleService;

    @Override
    public CommonResult<List<String>> getRoleList(Long userId) {
        //考虑缓存一下？
        SystemUserDO user = systemUserService.getById(userId);
        List<SystemRoleDO> roles = systemRoleService.listByIds(user.getRoleIds());
        return CommonResult.success(roles.stream().map(SystemRoleDO::getRoleName).toList());
    }
}
