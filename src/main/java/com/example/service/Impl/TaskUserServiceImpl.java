package com.example.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.TaskUser;
import com.example.mapper.TaskUserMapper;
import com.example.service.TaskUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskUserServiceImpl extends ServiceImpl<TaskUserMapper, TaskUser> implements TaskUserService {


    @Override
    public List<TaskUser> selectTaskFinish(Long taskIds) {
        return getTaskUsers(taskIds, 1);
    }

    @Override
    public List<TaskUser> selectTaskNotFinish(Long taskIds) {
        return getTaskUsers(taskIds, 0);
    }

    @Override
    public boolean saveTaskUser(TaskUser taskUser) {
        return save(taskUser);
    }

    private List<TaskUser> getTaskUsers(Long taskIds, Integer state) {
        LambdaQueryWrapper<TaskUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(taskIds != null, TaskUser::getTaskId, taskIds);
        lambdaQueryWrapper.eq(taskIds != null, TaskUser::getState, state);
        return list(lambdaQueryWrapper);
    }


}
