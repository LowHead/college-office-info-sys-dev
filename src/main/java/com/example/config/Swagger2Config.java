package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
//@EnableKnife4j
public class Swagger2Config {
    @Bean
    public Docket buildDocket() {
        return  new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .apiInfo(apiInfo())//调用下面apiInfo()方法
                .select()
                //Controller所在路径
                .apis(RequestHandlerSelectors.basePackage("com.example.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    public ApiInfo apiInfo() {
        Contact contact = new Contact("学院办公化信息管理平台","https://blog.csdn.net/m0_58941767","2277911380@qq.com");
        return  new ApiInfoBuilder()
                .title("springboot结合swagger2构建Restful API") //标题
                .description("springboot搭建办公化信息管理系统!") //描述(小标题)
                .termsOfServiceUrl("www.baidu.com") //网站链接
                .contact(contact) //链接显示的文字
                .version("1.0.0") //版本
                .build();
    }
}
