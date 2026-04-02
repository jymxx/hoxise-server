package cn.hoxise.common.security.satoken.config;

import cn.hoxise.common.security.satoken.core.PermissionSecurityService;
import cn.hoxise.common.security.satoken.core.SaTokenConfigure;
import cn.hoxise.common.security.satoken.core.StpInterfaceImpl;
import cn.hoxise.common.security.satoken.core.PermissionSecurityServiceImpl;
import cn.hoxise.common.security.satoken.web.SaTokenGlobalExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

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
    public StpInterfaceImpl stpInterface(){
        return new StpInterfaceImpl();
    }

    @Bean
    public SaTokenConfigure saTokenConfigure(){
        return new SaTokenConfigure();
    }

    @Bean
    @Order(-100)//处理顺序提前
    public SaTokenGlobalExceptionHandler saTokenGlobalExceptionHandler(){
        return new SaTokenGlobalExceptionHandler();
    }

}
