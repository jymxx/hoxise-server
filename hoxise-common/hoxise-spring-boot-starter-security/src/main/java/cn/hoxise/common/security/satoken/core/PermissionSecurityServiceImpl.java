package cn.hoxise.common.security.satoken.core;

import cn.hoxise.module.system.api.user.RolePermissionApi;
import jakarta.annotation.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 权限服务实现类
 *
 * @author hoxise
 * @since 2026/01/14 06:23:35
 */
public class PermissionSecurityServiceImpl implements PermissionSecurityService {

    @Resource
    private RolePermissionApi rolePermissionApi;

    @Override
    public List<String> getAllRole(long userid){
        if (userid == 0L){
            //所有权限
            return Collections.singletonList("*");
        }
        return rolePermissionApi.getRoleList(userid).getCheckedData();
    }

    @Override
    public List<String> getAllPermission(long userid){
        return new ArrayList<>();
    }
}
