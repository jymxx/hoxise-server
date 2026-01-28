package cn.hoxise.common.security.satoken.config;

import cn.hoxise.common.security.satoken.core.PermissionSecurityService;
import cn.hoxise.common.security.satoken.core.StpInterfaceImpl;
import cn.hoxise.common.security.satoken.core.PermissionSecurityServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
    public PermissionSecurityService permissionSecurityService(){
        return new PermissionSecurityServiceImpl();
    }

    @Bean
    @ConditionalOnBean(name = "permissionSecurityService")
    public StpInterfaceImpl stpInterface(){
        return new StpInterfaceImpl();
    }

}
