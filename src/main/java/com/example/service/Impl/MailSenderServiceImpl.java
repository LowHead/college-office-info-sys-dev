package com.example.service.Impl;

import com.example.service.MailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailSenderServiceImpl implements MailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    @Async
    public void sendEmail(String recipient, String subject, String text) {
        log.info(sender);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(recipient); //"2277911380@qq.com"
        message.setSubject(subject); //"测试邮箱发送消息"
        message.setText(text); //"这是一封测试邮件"
        javaMailSender.send(message);
    }
}
