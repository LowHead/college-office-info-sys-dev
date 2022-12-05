package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
@EnableAsync
public class CollegeOfficeInfoSysDevApplication{
    public static void main(String[] args) {
        SpringApplication.run(CollegeOfficeInfoSysDevApplication.class);
        log.info("项目启动成功...");
    }
}
