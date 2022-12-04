package com.example.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.component.RedisKeyExpirationJudgeHandle;
import com.example.domain.Task;
import com.example.domain.TaskUser;
import com.example.domain.User;
import com.example.mapper.TaskMapper;
import com.example.service.MailSenderService;
import com.example.service.TaskService;
import com.example.service.TaskUserService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.util.MailSenderConstant.SUBJECT;
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

    @Autowired
    private UserService userService;


    @Override
    @Transactional
    public boolean setTask(Task task) {
        taskIds.addFirst(task.getTaskId());
        save(task);
        TaskUser taskUser = new TaskUser(task.getTaskId(),StpUtil.getLoginIdAsLong(),0, LocalDateTime.now());
        taskUserService.saveTaskUser(taskUser);
        String key = PUBLISH_TASK_KEY + task.getTaskId();
        long sendTime = LocalDateTimeUtil.between(task.getDeadTime(), task.getCreateTime()).toNanos() * 1000 - 120 * 60;
        stringRedisTemplate.opsForValue().set(key, StpUtil.getLoginIdAsString(), sendTime, TimeUnit.SECONDS);
        return false;
    }

    @Override
    @Transactional
    public void handleRedisKeyExpiration(Message message, byte[] pattern) {
        Long id = taskIds.getLast();
        String key = PUBLISH_TASK_KEY + id;
        if (key.equals(message.toString())) {
            taskIds.removeLast();
            List<TaskUser> taskUsers = taskUserService.selectTaskNotFinish(id);
            for (TaskUser taskUser : taskUsers) {
                User user = userService.getUserById(taskUser.getUserId());
                Task task = getById(taskUser.getTaskId());
                String text = user.getUsername() + "你好，您的任务" + task.getTaskTitle() + "将于两小时后到期,请及时进行提交";
                mailSenderService.sendEmail(user.getUserMail(), SUBJECT, text);
            }
        }
    }


}
