package com.example.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/code")
@Api(tags ="验证码接口")
public class GetCodeController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @GetMapping("/getCode")
    public void createCaptcha(HttpServletResponse response, HttpServletRequest request ) throws IOException {
        // 定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        // 图形验证码写出，可以写出到文件，也可以写出到流
        lineCaptcha.write(response.getOutputStream());
        String code = lineCaptcha.getCode();
        stringRedisTemplate.opsForValue().set("code",code,60, TimeUnit.SECONDS);
        // 关闭流
        response.getOutputStream().close();
    }
}
