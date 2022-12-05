package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.Task;

public interface TaskService extends IService<Task> {

    /**
     * 添加一条任务
     * @param task 任务相关消息
     * @return 是否成功
     */
    boolean setTask(Task task);
}
