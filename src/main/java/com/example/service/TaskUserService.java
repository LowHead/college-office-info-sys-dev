package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.TaskUser;

import java.util.List;

public interface TaskUserService extends IService<TaskUser> {

    /**
     * 根据任务id获取已完成用户-任务消息
     * @param taskIds 任务id
     * @return 用户-任务消息
     */
    List<TaskUser> selectTaskFinish(Long taskIds);

    /**
     * 根据任务id获取未完成用户-任务消息
     * @param taskIds 任务id
     * @return 用户-任务消息
     */
    List<TaskUser> selectTaskNotFinish(Long taskIds);

    /**
     * 保存用户-任务消息
     * @param taskUser 用户-任务消息
     * @return 是否成功
     */
    boolean saveTaskUser(TaskUser taskUser);


}
