package com.example.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.example.common.Result;
import com.example.domain.Task;
import com.example.dto.TaskDto;
import com.example.service.TaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping("/save")
    public Result save(@RequestBody TaskDto taskDto) {
        Task task = new Task();
        BeanUtils.copyProperties(taskDto,task);
        task.setTaskStatus(1);
        task.setTaskSort(0);
        task.setCreatePerson(StpUtil.getLoginIdAsLong());
        taskService.setTask(task);
        return Result.success(null, "任务添加成功");
    }


}
