package com.example.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.example.util.MailSenderConstant.SUBJECT;
import static com.example.util.RedisConstant.PUBLISH_TASK_KEY;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

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
    public List<User> getFinishUser(Long taskId) {
        List<TaskUser> taskUsers = taskUserService.selectTaskFinish(taskId);
        List<User> users = new ArrayList<>();
        for (TaskUser taskUser : taskUsers) {
            User user = userService.getUserById(taskUser.getUserId());
            users.add(user);
        }
        return users;
    }

    @Override
    public List<User> getNotFinishUser(Long taskId) {
        List<TaskUser> taskUsers = taskUserService.selectTaskNotFinish(taskId);
        List<User> users = new ArrayList<>();
        for (TaskUser taskUser : taskUsers) {
            User user = userService.getUserById(taskUser.getUserId());
            users.add(user);
        }
        return users;
    }

    @Override
    @Transactional
    public boolean setTask(Task task) {
        taskIds.addFirst(task.getTaskId());
        save(task);
        TaskUser taskUser = new TaskUser(task.getTaskId(),StpUtil.getLoginIdAsLong(),0, LocalDateTime.now());
        taskUserService.saveTaskUser(taskUser);

        LocalDateTime deadTime = LocalDateTimeUtil.offset(task.getDeadTime(), -2, ChronoUnit.HOURS);
        String format = LocalDateTimeUtil.format(deadTime, DatePattern.NORM_DATETIME_FORMATTER);
        Date date = DateUtil.parse(format);

        Timer timer = new Timer();
        TimerTask sc = new TimerTask() {
            @Override
            public void run() {
                List<TaskUser> taskUsers = taskUserService.selectTaskNotFinish(task.getTaskId());
                for (TaskUser taskUser : taskUsers) {
                    User user = userService.getUserById(taskUser.getUserId());
                    Task task = getById(taskUser.getTaskId());
                    String text = user.getUsername() + "你好，您的任务" + task.getTaskTitle() + "将于两小时后到期,请及时进行提交";
                    mailSenderService.sendEmail(user.getUserMail(), SUBJECT, text);
                }
            }
        };

        timer.schedule(sc,date);

        return true;
    }


}
