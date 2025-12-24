package cn.hoxise.common.ai.service;

import cn.hoxise.system.api.user.RolePermissionApi;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: hoxise
 * @Description:
 * @Date: 2024/3/3 1:17
 */
@Service
public class PermissionSecurityServiceImpl implements PermissionSecurityService {

    @Resource
    private RolePermissionApi rolePermissionApi;

    @Override
    public List<String> getAllRole(long userid){
        return rolePermissionApi.getRoleList(userid);
    }

    @Override
    public List<String> getAllPermission(long userid){
        return new ArrayList<>();
    }
}
