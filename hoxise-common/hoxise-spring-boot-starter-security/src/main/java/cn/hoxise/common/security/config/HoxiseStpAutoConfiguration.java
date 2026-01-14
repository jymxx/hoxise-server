package cn.hoxise.common.security.config;

import cn.hoxise.common.security.core.PermissionSecurityService;
import cn.hoxise.common.security.core.PermissionSecurityServiceImpl;
import cn.hoxise.common.security.core.StpInterfaceImpl;
import cn.hoxise.system.api.user.RolePermissionApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 自动配置
 *
 * @author hoxise
 * @since 2026/01/14 06:22:07
 */
@AutoConfiguration
public class HoxiseStpAutoConfiguration {

    @Bean
    public PermissionSecurityService permissionSecurityService(RolePermissionApi rolePermissionApi){
        return new PermissionSecurityServiceImpl(rolePermissionApi);
    }

    @Bean
    public StpInterfaceImpl stpInterface(PermissionSecurityService permissionSecurityService){
        return new StpInterfaceImpl(permissionSecurityService);
    }

}
