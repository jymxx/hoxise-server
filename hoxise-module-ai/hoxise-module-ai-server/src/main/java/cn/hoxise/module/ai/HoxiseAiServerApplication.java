package cn.hoxise.module.ai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author hoxise
 * @since 2026/1/18 下午8:11
 */
@MapperScan("cn.hoxise.module.ai.dal.mapper")
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"cn.hoxise.module.system.api", "cn.hoxise.module.movie.api"})
@EnableAsync
public class HoxiseAiServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(HoxiseAiServerApplication.class, args);
    }
}
