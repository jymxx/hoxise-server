package cn.hoxise.common.ai.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.hoxise.common.ai.service.PermissionSecurityService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: hoxise
 * @Description: satoken配置
 * @Date: 2024/3/3 1:29
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private PermissionSecurityService permissionSecurityService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return permissionSecurityService.getAllPermission(Long.parseLong(loginId.toString()));
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return permissionSecurityService.getAllRole(Long.parseLong(loginId.toString()));
    }
}
