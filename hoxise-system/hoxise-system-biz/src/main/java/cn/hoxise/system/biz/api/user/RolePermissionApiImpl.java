package cn.hoxise.system.biz.api.user;

import cn.hoxise.system.api.user.RolePermissionApi;
import cn.hoxise.system.biz.dal.entity.SystemRoleDO;
import cn.hoxise.system.biz.dal.entity.SystemUserDO;
import cn.hoxise.system.biz.service.user.SystemRoleService;
import cn.hoxise.system.biz.service.user.SystemUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author hoxise
 * @Description:
 * @Date: 2024/3/3 2:48
 */
@Service
public class RolePermissionApiImpl implements RolePermissionApi {

    @Resource private SystemUserService systemUserService;
    @Resource private SystemRoleService systemRoleService;

    @Override
    public List<String> getRoleList(long userId) {
        if (userId == 0L){
            return Collections.singletonList("*");
        }
        SystemUserDO user = systemUserService.getById(userId);
        List<SystemRoleDO> roles = systemRoleService.listByIds(user.getRoleIds());
        return roles.stream().map(SystemRoleDO::getRoleName).toList();
    }

}
