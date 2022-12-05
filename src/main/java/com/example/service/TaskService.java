package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.Task;
import com.example.domain.User;

import java.util.List;

public interface TaskService extends IService<Task> {

    /**
     * 添加一条任务
     * @param task 任务相关消息
     * @return 是否成功
     */
    boolean setTask(Task task);

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
