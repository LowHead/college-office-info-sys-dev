package com.example.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.component.RedisKeyExpirationJudgeHandle;
import com.example.domain.Task;
import com.example.domain.TaskUser;
import com.example.mapper.TaskMapper;
import com.example.service.MailSenderService;
import com.example.service.TaskService;
import com.example.service.TaskUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.util.RedisConstant.PUBLISH_TASK_KEY;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService, RedisKeyExpirationJudgeHandle {

    private final LinkedList<Long> taskIds = new LinkedList<>();

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private TaskUserService taskUserService;


    @Override
    public boolean setTask(Task task) {
        taskIds.addLast(task.getTaskId());
        save(task);
        String key = PUBLISH_TASK_KEY + task.getTaskId();
        long sendTime = LocalDateTimeUtil.between(task.getDeadTime(), task.getCreateTime()).toNanos() * 1000 - 120 * 60;
        stringRedisTemplate.opsForValue().set(key, StpUtil.getLoginIdAsString(), sendTime, TimeUnit.SECONDS);
        return false;
    }

    @Override
    public void handleRedisKeyExpiration(Message message, byte[] pattern) {
        Long id = taskIds.getFirst();
        String key = PUBLISH_TASK_KEY + id;
        if (key.equals(message.toString())) {
            List<TaskUser> users = taskUserService.selectTaskNotFinish(id);
            mailSenderService.sendEmail();
        }
    }
}
