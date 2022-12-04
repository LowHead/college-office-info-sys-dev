package com.example.component;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class RedisKeyExpirationListener  extends KeyExpirationEventMessageListener {


    @Autowired
    private ApplicationContext applicationContext;


    private final List<RedisKeyExpirationJudgeHandle> redisKeyExpirationJudgeHandleList = new ArrayList<>();

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }


    @Override
    public void onMessage(Message message, byte[] pattern) {

        log.info("监听Redis key过期，key：{}，channel：{}", message.toString(), new String(pattern));


        Map<String, RedisKeyExpirationJudgeHandle> beans = applicationContext.getBeansOfType(RedisKeyExpirationJudgeHandle.class);
        Set<Map.Entry<String, RedisKeyExpirationJudgeHandle>> entries = beans.entrySet();
        for (Map.Entry<String, RedisKeyExpirationJudgeHandle> entry : entries) {
            log.info("获取到实现类名称为：" + entry.getKey());
            System.out.println(entry.getValue());
            if (!checkClassWhetherExistence(entry.getValue())) {
                redisKeyExpirationJudgeHandleList.add(entry.getValue());
            }
        }

        for (RedisKeyExpirationJudgeHandle handle : redisKeyExpirationJudgeHandleList) {
            log.error("集合中的实现类有：{}", handle);
            handle.handleRedisKeyExpiration(message,pattern);
        }

    }

    private boolean checkClassWhetherExistence(RedisKeyExpirationJudgeHandle redisKeyExpirationJudgeHandle) {
        for (RedisKeyExpirationJudgeHandle handle : redisKeyExpirationJudgeHandleList) {
            if (handle == redisKeyExpirationJudgeHandle) {
                return true;
            }
        }
        return false;
    }


}
