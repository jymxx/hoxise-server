package cn.hoxise.module.movie;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author hoxise
 * @since 2026/1/18 下午8:11
 */
@MapperScan("cn.hoxise.module.movie.dal.mapper")
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cn.hoxise.module.system.api")
public class HoxiseMovieServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(HoxiseMovieServerApplication.class, args);
    }
}
