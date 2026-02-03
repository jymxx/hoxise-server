package cn.hoxise.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Hoxise
 */
@MapperScan("cn.hoxise.**.mapper")
@EnableAsync
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"cn.hoxise.server"
        ,"cn.hoxise.module.system"
        ,"cn.hoxise.module.movie"
        ,"cn.hoxise.module.ai"
        ,"cn.hoxise.module.self"})
public class HoxiseWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(HoxiseWebApplication.class, args);
    }

}
