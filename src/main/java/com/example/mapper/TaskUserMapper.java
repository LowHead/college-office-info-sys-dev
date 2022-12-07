package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.TaskUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskUserMapper extends BaseMapper<TaskUser> {
}
