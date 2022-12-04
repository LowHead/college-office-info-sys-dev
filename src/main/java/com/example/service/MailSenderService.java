package com.example.service;

public interface MailSenderService {
    void sendEmail(String recipient, String subject, String text);
}
