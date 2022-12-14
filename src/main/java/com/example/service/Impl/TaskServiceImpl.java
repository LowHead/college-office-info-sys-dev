package com.example.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.Result;
import com.example.domain.*;
import com.example.dto.TaskDto;
import com.example.mapper.TaskMapper;
import com.example.service.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.example.util.MailSenderConstant.SUBJECT;

@Slf4j
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private TaskUserService taskUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private UrlService urlService;

    @Autowired
    private TaskUrlService taskUrlService;

    @Override
    public List<User> getFinishUser(Long taskId) {
        LambdaQueryWrapper<TaskUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(taskId != null, TaskUser::getTaskId, taskId);
        lambdaQueryWrapper.eq(taskId != null, TaskUser::getState, 1);
        return getUsers(lambdaQueryWrapper);
    }

    @Override
    public List<User> getNotFinishUser(Long taskId) {
        LambdaQueryWrapper<TaskUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(taskId != null, TaskUser::getTaskId, taskId);
        lambdaQueryWrapper.eq(taskId != null, TaskUser::getState, 0)
                .or().eq(TaskUser::getState, 2);
        return getUsers(lambdaQueryWrapper);
    }

    @NotNull
    private List<User> getUsers(LambdaQueryWrapper<TaskUser> lambdaQueryWrapper) {
        List<TaskUser> taskUsers = taskUserService.list(lambdaQueryWrapper);
        List<User> users = new ArrayList<>();
        for (TaskUser taskUser : taskUsers) {
            User user = userService.getById(taskUser.getUserId());
            users.add(user);
        }
        return users;
    }

    @Override
    public boolean repulse(List<Long> taskIds) {

        LambdaUpdateWrapper<TaskUser> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.in(TaskUser::getTaskId,taskIds).set(TaskUser::getState,2);
        if (!taskUserService.update(lambdaUpdateWrapper)) {
            return false;
        }

        List<TaskUser> taskUsers = taskUserService.listByIds(taskIds);
        for (TaskUser taskUser : taskUsers) {
            User user = userService.getById(taskUser.getUserId());
            Task task = getById(taskUser.getTaskId());
            String subject = "??????????????????";
            String text = "?????????????????????" + task.getTaskTitle() + "???????????????????????????????????????????????????!";
            mailSenderService.sendEmail(user.getUserMail(),subject,text);
        }
        return true;
    }

    @Override
    @Transactional
    public Result addTask(TaskDto taskDto) {
        Task task = new Task();
        BeanUtils.copyProperties(taskDto,task);
        task.setCreatePerson(StpUtil.getLoginIdAsLong());
        task.setCreateTime(LocalDateTime.now());
        task.setUpdateTime(LocalDateTime.now());
        //??????task???
        this.save(task);

        int flag = 0;

        List<Url> urls = taskDto.getUrls();
        for (Url url : urls){
            url.setUserId(StpUtil.getLoginIdAsLong());
            url.setCreateTime(LocalDateTime.now());
            //??????url???
            urlService.save(url);

            flag = 0;
            if (StpUtil.getSession().get("admin") == null) flag = 1;

            TaskUrl taskUrl = new TaskUrl();
            taskUrl.setTaskId(task.getTaskId());
            taskUrl.setUrlId(url.getUrlId());
            taskUrl.setSource(flag);
//            ??????task_url???
            taskUrlService.save(taskUrl);
        }

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(User::getUserMajorId,taskDto.getMajors());

        List<User> userList = userService.list(queryWrapper);
        for (User user : userList){
            taskUserService.save(new TaskUser(task.getTaskId(),user.getUserId(),null,null));
        }

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
                    String text = user.getUsername() + "????????????????????????" + task.getTaskTitle() + "???????????????????????????,?????????????????????";
                    mailSenderService.sendEmail(user.getUserMail(), SUBJECT, text);
                }
            }
        };

        timer.schedule(sc,date);


        return Result.success(null,"??????????????????");
    }

    @Override
    @Transactional
    public Result deleteTask(Long taskId) {
        //??????task???
        boolean b = this.removeById(taskId);

        if (!b){
            return Result.failure("????????????!?????????????????????");
        }

        //??????task_user???
        LambdaQueryWrapper<TaskUser> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(TaskUser::getTaskId,taskId);
        taskUserService.remove(queryWrapper1);

        //??????task_url???
        LambdaQueryWrapper<TaskUrl> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(TaskUrl::getTaskId,taskId);
        List<TaskUrl> list = taskUrlService.list(queryWrapper2);
        if (!list.isEmpty()){
            //??????url???
            for (TaskUrl taskUrl : list){
                urlService.removeById(taskUrl.getUrlId());
            }
        }
        taskUrlService.remove(queryWrapper2);

        return Result.success(null,"??????????????????");
    }

    @Override
    @Transactional
    public Result updateTask(TaskDto taskDto) {

        Task task = new Task();
        BeanUtils.copyProperties(taskDto,task);
        boolean b = this.updateById(task);
        if (!b) return Result.failure("?????????????????????????????????");

        if(taskDto.getUrls() != null){
            //??????task_url???
            LambdaQueryWrapper<TaskUrl> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TaskUrl::getTaskId,taskDto.getTaskId());
            List<TaskUrl> list = taskUrlService.list(queryWrapper);
            if (!list.isEmpty()){
                //??????url???
                for (TaskUrl taskUrl : list){
                    urlService.removeById(taskUrl.getUrlId());
                }
            }
            taskUrlService.remove(queryWrapper);

            int flag = 0;
            List<Url> urls = taskDto.getUrls();
            for (Url url : urls){
                url.setUserId(StpUtil.getLoginIdAsLong());
                url.setCreateTime(LocalDateTime.now());
                //??????url???
                urlService.save(url);

                flag = 0;
                if (StpUtil.getSession().get("admin") == null) flag = 1;

                TaskUrl taskUrl = new TaskUrl();
                taskUrl.setTaskId(task.getTaskId());
                taskUrl.setUrlId(url.getUrlId());
                taskUrl.setSource(flag);
    //            ??????task_url???
                taskUrlService.save(taskUrl);
            }
        }
        return Result.success(null,"??????????????????");
    }

    @Override
    public Result pageTask(int page, int size, String name) {
        return Result.success(null, "???????????????");
    }


}
