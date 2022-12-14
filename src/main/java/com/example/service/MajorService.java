package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.Major;
import com.example.dto.MajorDto;
import com.example.exception.DuplicateMajorNameException;
import com.example.exception.DuplicatePositionException;

public interface MajorService extends IService<Major> {

    /**
     * 添加专业与专业相关负责人
     * @param majorDto 专业与负责人相关信息传输类
     * @return 是否成功
     */
    Boolean addMajorAndUser(MajorDto majorDto);
}
