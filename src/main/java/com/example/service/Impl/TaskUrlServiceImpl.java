package com.example.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.TaskUrl;
import com.example.mapper.TaskUrlMapper;
import com.example.service.TaskUrlService;
import org.springframework.stereotype.Service;

@Service
public class TaskUrlServiceImpl extends ServiceImpl<TaskUrlMapper, TaskUrl> implements TaskUrlService {
}
