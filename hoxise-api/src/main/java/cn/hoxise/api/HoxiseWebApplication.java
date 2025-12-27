package cn.hoxise.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@ComponentScan({"cn.hoxise.*"})
@MapperScan("cn.hoxise.**.mapper")
@EnableAsync
@SpringBootApplication
public class HoxiseWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(HoxiseWebApplication.class, args);
    }

}
