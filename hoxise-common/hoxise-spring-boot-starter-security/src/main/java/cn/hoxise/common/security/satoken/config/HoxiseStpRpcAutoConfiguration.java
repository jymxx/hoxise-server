package cn.hoxise.common.security.satoken.config;

import cn.hoxise.module.system.api.user.RolePermissionApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author hoxise
 * @since 2026/1/19 上午12:03
 */
@AutoConfiguration
@EnableFeignClients(clients = {RolePermissionApi.class})
public class HoxiseStpRpcAutoConfiguration {
}
