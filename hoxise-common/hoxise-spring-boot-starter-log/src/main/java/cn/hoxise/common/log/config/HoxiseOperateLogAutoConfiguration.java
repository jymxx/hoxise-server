package cn.hoxise.common.log.config;

import cn.hoxise.common.log.core.aop.OperateLogAspect;
import cn.hoxise.common.log.core.service.OperateLogBaseService;
import cn.hoxise.common.log.core.service.OperateLogBaseServiceImpl;
import cn.hoxise.system.api.log.OperateLogApi;
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
    public OperateLogBaseService operateLogBaseService(OperateLogApi operateLogApi) {
        return new OperateLogBaseServiceImpl(operateLogApi);
    }
}
