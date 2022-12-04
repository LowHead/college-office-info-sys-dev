package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.Task;

public interface TaskService extends IService<Task> {

    boolean setTask(Task task);
}
