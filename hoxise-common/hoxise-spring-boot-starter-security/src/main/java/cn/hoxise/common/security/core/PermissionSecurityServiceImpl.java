package cn.hoxise.common.security.core;

import cn.hoxise.system.api.user.RolePermissionApi;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;


/**
 * 权限服务实现类
 *
 * @author hoxise
 * @since 2026/01/14 06:23:35
 */
@RequiredArgsConstructor
public class PermissionSecurityServiceImpl implements PermissionSecurityService {

    private final RolePermissionApi rolePermissionApi;

    @Override
    public List<String> getAllRole(long userid){
        return rolePermissionApi.getRoleList(userid);
    }

    @Override
    public List<String> getAllPermission(long userid){
        return new ArrayList<>();
    }
}
