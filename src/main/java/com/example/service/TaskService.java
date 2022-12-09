package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.Result;
import com.example.domain.Task;
import com.example.domain.User;
import com.example.dto.TaskDto;

import java.util.List;

public interface TaskService extends IService<Task> {

    /**
     * 添加一条任务
     *
     * @param taskDto @return 是否成功
     * @return
     */
    Result addTask(TaskDto taskDto);

    /**
     * 删除一条任务
     * @param taskId
     * @return
     */
    Result deleteTask(Long taskId);

    /**
     * 修改任务
     * @param taskDto
     * @return
     */
    Result updateTask(TaskDto taskDto);

    /**
     * 分页查询所有任务
     * @param page
     * @param size
     * @param name
     * @return
     */
    Result pageTask(int page, int size, String name);

    /**
     * 根据任务id获取已完成任务用户名称
     * @param taskId 任务id
     * @return 用户集合
     */
    List<User> getFinishUser(Long taskId);

    /**
     * 根据任务id获取未完成任务用户名称
     * @param taskId 任务id
     * @return 用户集合
     */
    List<User> getNotFinishUser(Long taskId);
}
