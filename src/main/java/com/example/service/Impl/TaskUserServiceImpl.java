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
        LambdaQueryWrapper<TaskUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(taskIds != null, TaskUser::getTaskId, taskIds);
        lambdaQueryWrapper.eq(taskIds != null, TaskUser::getState, 1);
        return list(lambdaQueryWrapper);
    }

    @Override
    public List<TaskUser> selectTaskNotFinish(Long taskIds) {
        LambdaQueryWrapper<TaskUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(taskIds != null, TaskUser::getTaskId, taskIds);
        lambdaQueryWrapper.eq(taskIds != null, TaskUser::getState, 0)
                .or().eq(TaskUser::getState, 2);
        return list(lambdaQueryWrapper);
    }

    @Override
    public boolean saveTaskUser(TaskUser taskUser) {
        return save(taskUser);
    }



}
