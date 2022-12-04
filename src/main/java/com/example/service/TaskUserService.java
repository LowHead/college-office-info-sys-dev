package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.TaskUser;

import java.util.List;

public interface TaskUserService extends IService<TaskUser> {

    List<TaskUser> selectTaskFinish(Long taskIds);

    List<TaskUser> selectTaskNotFinish(Long taskIds);

    boolean saveTaskUser(TaskUser taskUser);
}
