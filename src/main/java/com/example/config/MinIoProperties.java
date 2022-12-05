package com.example.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
//@ConfigurationProperties(prefix = "minio")
public class MinIoProperties {

    /**
     * minio地址+端口号
     */
    @Value("${minio.url}")
    private String url;

    /**
     * minio用户名
     */
    @Value("${minio.accessKey}")
    private String accessKey;

    /**
     * minio密码
     */
    @Value("${minio.secretKey}")
    private String secretKey;

    /**
     * 文件桶的名称
     */
    @Value("${minio.bucketName}")
    private String bucketName;

}
