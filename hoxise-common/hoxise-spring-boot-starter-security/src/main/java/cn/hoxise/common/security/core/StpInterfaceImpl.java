package cn.hoxise.common.security.core;

import cn.dev33.satoken.stp.StpInterface;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * SaToken配置
 *
 * @author hoxise
 * @since 2024/3/3 1:29
 */
@RequiredArgsConstructor
public class StpInterfaceImpl implements StpInterface {

    private final PermissionSecurityService permissionSecurityService;

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
