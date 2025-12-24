package cn.hoxise.system.api.user;

import java.util.List;

/**
 * @author hoxise
 * @Description:
 * @Date: 2024/3/3 2:47
 */
public interface RolePermissionApi {

   List<String> getRoleList(long userId);
}
