package cn.hoxise.common.ai.service;

import java.util.List;

/**
 * @Author: hoxise
 * @Description:
 * @Date: 2024/3/3 1:26
 */
public interface PermissionSecurityService {
    List<String> getAllRole(long userid);

    List<String> getAllPermission(long userid);
}
