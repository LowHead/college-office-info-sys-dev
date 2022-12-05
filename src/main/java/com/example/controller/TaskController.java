package com.example.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.common.Result;
import com.example.domain.Task;
import com.example.domain.TaskUser;
import com.example.domain.User;
import com.example.dto.TaskDto;
import com.example.service.TaskService;
import com.example.service.TaskUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/task")
@Api
public class TaskController {

    @Resource
    private TaskService taskService;

    @Resource
    private TaskUserService taskUserService;


    @PostMapping("/save")
    public Result save(@RequestBody TaskDto taskDto) {
        Task task = new Task();
        BeanUtils.copyProperties(taskDto,task);
        task.setTaskStatus(1);
        task.setTaskSort(0);
        task.setCreatePerson(StpUtil.getLoginIdAsLong());
        taskService.setTask(task);
        return Result.success(null, "任务发布成功");
    }

    @GetMapping("/finish")
    public Result finish(@RequestParam("taskId") Long taskId) {
        if (taskId == null) {
            return Result.failure("传入的任务主键为空");
        }
        List<User> users = taskService.getFinishUser(taskId);
        return Result.success(users,"获取已完成任务用户成功");
    }

    @GetMapping("/notFinish")
    public Result notFinish(@RequestParam("taskId") Long taskId) {
        if (taskId == null) {
            return Result.failure("传入的任务主键为空");
        }
        List<User> users = taskService.getNotFinishUser(taskId);
        return Result.success(users,"获取已完成任务用户成功");
    }

    @PutMapping("/repulse")
    public Result repulse(@RequestParam("taskId") List<Long> taskIds) {
        if (taskIds == null) {
            return Result.failure("传入的任务主键为空");
        }
        boolean ok = taskUserService.repulse(taskIds);
        if (!ok) {
            return Result.failure("打回失败，请重新提交");
        }
        return Result.success(null, "打回成功");
    }


}
