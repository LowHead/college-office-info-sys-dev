package com.example.component;

import com.example.service.MailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("RedisKeyExpirationJudgeHandle1")
@Slf4j
public class RedisKeyExpirationJudgeHandle1{

    @Resource
    private MailSenderService mailSenderService;

    public void handleRedisKeyExpiration(Message message, byte[] pattern) {
        String key = message.toString();
        if (key.equals("news:mail:task:id" + "2")) {
            mailSenderService.sendEmail("","","");
        }
    }
}
