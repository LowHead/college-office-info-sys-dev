package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 */

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  //项目中的所有接口都支持跨域
                .allowedOriginPatterns("*") //所有地址都可以访问，也可以配置具体地址
                .allowCredentials(true)  //注意allowedOrigins("*")和allowCredentials(true)为true时候会出现错误需要改成allowedOriginPatterns("*")或者单独指定接口allowedOrigins("http//www.baidu.com")
                .allowedMethods("*")    //"GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"
                .maxAge(3600);  // 跨域允许时间
    }
}
