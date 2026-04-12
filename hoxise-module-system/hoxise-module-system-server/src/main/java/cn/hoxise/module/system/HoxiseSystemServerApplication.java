package cn.hoxise.module.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author hoxise
 * @since 2026/1/16 下午10:28
 */
@MapperScan("cn.hoxise.module.system.dal.mapper")
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableAsync
@EnableScheduling
public class HoxiseSystemServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HoxiseSystemServerApplication.class, args);
    }
}
