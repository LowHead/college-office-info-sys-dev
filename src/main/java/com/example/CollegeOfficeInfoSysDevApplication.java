package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ServletComponentScan
public class CollegeOfficeInfoSysDevApplication {
    public static void main(String[] args) {
        SpringApplication.run(CollegeOfficeInfoSysDevApplication.class);
    }
}
