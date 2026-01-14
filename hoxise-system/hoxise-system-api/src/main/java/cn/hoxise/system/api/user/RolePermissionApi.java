package cn.hoxise.system.api.user;

import java.util.List;

/**
 * 暴露给其它模块的角色权限接口
 *
 * @author hoxise
 * @since 2026/01/14 06:12:43
 */
public interface RolePermissionApi {

   /**
    * 获取用户的角色列表
    *
    * @param userId 用户id
    * @return java.util.List<java.lang.String>
    * @author hoxise
    * @since 2026/01/14 06:13:16
    */
   List<String> getRoleList(long userId);
}
