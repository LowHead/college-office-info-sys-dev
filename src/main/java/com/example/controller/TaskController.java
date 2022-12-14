package com.example.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.example.common.Result;
import com.example.domain.Task;
import com.example.domain.User;
import com.example.dto.TaskDto;
import com.example.service.TaskService;
import com.example.service.TaskUserService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/task")
@Api(tags = "任务接口")
public class TaskController {

    @Resource
    private TaskService taskService;

    @Resource
    private TaskUserService taskUserService;


    @PostMapping("/save")
    @ApiParam(name = "taskDto", value = "任务传输实体类", required = true)
    @ApiOperation("提交任务")
    public Result save(@RequestBody TaskDto taskDto) {
        return taskService.addTask(taskDto);
    }

    @DeleteMapping("/delete")
    @ApiImplicitParams(@ApiImplicitParam(name = "taskId", value = "任务主键id", required = true))
    @ApiOperation("删除任务")
    public Result delete(@RequestParam("taskId") Long taskId) {
        return taskService.deleteTask(taskId);
    }

    @PutMapping("/update")
    @ApiParam(name = "taskDto", value = "任务传输实体类", required = true)
    @ApiOperation("更新任务")
    public Result update(@RequestBody TaskDto taskDto) {
        return taskService.updateTask(taskDto);
    }

    @GetMapping("/page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = false),
            @ApiImplicitParam(name = "size", value = "页大小", required = false),
            @ApiImplicitParam(name = "name", value = "名称", required = true),
    })
    @ApiOperation("分页获取任务列表")
    public Result page(int page, int size, String name) {
        return taskService.pageTask(page, size, name);
    }

    @GetMapping("/finish")
    @ApiImplicitParams(@ApiImplicitParam(name = "taskId", value = "任务主键id", required = true))
    @ApiOperation("获取已完成任务")
    public Result finish(@RequestParam("taskId") Long taskId) {
        if (taskId == null) {
            return Result.failure("传入的任务主键为空");
        }
        List<User> users = taskService.getFinishUser(taskId);
        return Result.success(users, "获取已完成任务用户成功");
    }

    @GetMapping("/notFinish")
    @ApiImplicitParams(@ApiImplicitParam(name = "taskId", value = "任务主键id", required = true))
    @ApiOperation("获取未完成任务")
    public Result notFinish(@RequestParam("taskId") Long taskId) {
        if (taskId == null) {
            return Result.failure("传入的任务主键为空");
        }
        List<User> users = taskService.getNotFinishUser(taskId);
        return Result.success(users, "获取已完成任务用户成功");
    }

    @PutMapping("/repulse")
    @ApiImplicitParams(@ApiImplicitParam(name = "taskIds", value = "任务主键id集合", required = true))
    @ApiOperation("打回任务")
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
