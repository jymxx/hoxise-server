package cn.hoxise.common.security.operatelog.config;

import cn.hoxise.module.system.api.log.OperateLogApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 微服务
 *
 * @author hoxise
 * @since 2026/1/19 上午12:02
 */
@AutoConfiguration
@EnableFeignClients(clients = {OperateLogApi.class})
public class HoxiseOperateLogRpcAutoConfiguration {
}
