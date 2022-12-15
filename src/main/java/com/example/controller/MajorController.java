package com.example.controller;

import com.example.common.Result;
import com.example.dto.MajorDto;
import com.example.exception.DuplicateMajorNameException;
import com.example.exception.DuplicatePositionException;
import com.example.service.MajorService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/majors")
@Api(tags = "专业接口")
public class MajorController {

    @Resource
    private MajorService majorService;

    @PostMapping("/save")
    @ApiOperation("添加专业以及专业负责人")
    @ApiParam(name = "majorDto", value = "专业与专业负责人信息", required = true)
    public Result save(@RequestBody @Valid MajorDto majorDto) {
        Boolean majorAndUser = majorService.addMajorAndUser(majorDto);
        if (!majorAndUser) {
            return Result.failure("添加专业以及专业负责人失败");
        }
        return Result.success(null, "添加专业以及专业负责人成功");
    }
}
