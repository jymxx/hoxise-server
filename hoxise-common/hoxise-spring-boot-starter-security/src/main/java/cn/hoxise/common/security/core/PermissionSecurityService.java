package cn.hoxise.common.security.core;

import java.util.List;

/**
 * 角色权限获取
 *
 * @author hoxise
 * @since 2026/01/14 06:22:17
 */
public interface PermissionSecurityService {
    /**
     * 获取用户所有角色
     *
     * @param userid 用户id
     * @return 角色列表
     * @author hoxise
     * @since 2026/01/14 06:22:45
     */
    List<String> getAllRole(long userid);

    /**
     * 获取用户所有权限
     *
     * @param userid 用户id
     * @return 权限列表
     * @author hoxise
     * @since 2026/01/14 06:23:13
     */
    List<String> getAllPermission(long userid);
}
