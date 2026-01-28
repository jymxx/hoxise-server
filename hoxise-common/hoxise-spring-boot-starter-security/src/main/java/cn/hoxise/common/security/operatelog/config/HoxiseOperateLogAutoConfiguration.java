package cn.hoxise.common.security.operatelog.config;

import cn.hoxise.common.security.operatelog.core.aop.OperateLogAspect;
import cn.hoxise.common.security.operatelog.core.service.OperateLogBaseService;
import cn.hoxise.common.security.operatelog.core.service.OperateLogBaseServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 操作日志自动配置
 *
 * @author hoxise
 * @since 2026/1/14 上午7:38
 */
@AutoConfiguration
public class HoxiseOperateLogAutoConfiguration {

    @Bean
    public OperateLogAspect operateLogAspect() {
        return new OperateLogAspect();
    }

    @Bean
    public OperateLogBaseService operateLogBaseService() {
        return new OperateLogBaseServiceImpl();
    }
}
