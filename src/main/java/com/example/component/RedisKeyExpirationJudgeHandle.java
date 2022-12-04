package com.example.component;

import org.springframework.data.redis.connection.Message;

public interface RedisKeyExpirationJudgeHandle {
    void handleRedisKeyExpiration(Message message, byte[] pattern);
}
