package cn.hoxise.common.framework.core.swagger;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * SpringDoc配置类
 *
 * @author hoxise
 * @since 2026/01/14 06:16:01
 */
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenApi() {
        return new OpenAPI()
                // 接口文档标题
                .info(new Info().title("hoxiseAPI接口文档")
                        // 接口文档简介
                        .description("这是基于Knife4j OpenApi3的接口文档")
                        // 接口文档版本
                        .version("v1.0")
                        // 开发者联系方式
                        .contact(new Contact().name("hoxise").email("")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringBoot3基础框架")
                        .url("https://hxoise.cn"));
    }

}
