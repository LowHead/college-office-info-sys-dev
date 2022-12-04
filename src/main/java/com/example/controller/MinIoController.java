package com.example.controller;

import com.example.common.Result;
import com.example.component.MinIoUtil;
import com.example.config.MinIoProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/common")
@Api(tags = {"文件上传与下载"})
public class MinIoController {

    @Autowired
    MinIoProperties minIoProperties;

    @ApiOperation(value = "上传文件")
    @PostMapping(value = "/upload")
    public Result upload(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws Exception {
        String fileUrl = MinIoUtil.upload(minIoProperties.getBucketName(), file);
        String url = MinIoUtil.getFileUrl(minIoProperties.getBucketName(), file.getOriginalFilename());
        return Result.success(url,"文件上传成功，已返回文件下载链接");

//        return MinIoUtil.uploadFile(minIoProperties.getBucketName(),file,response) != null ? Result.success(file.getOriginalFilename(),"上传成功") : Result.failure("上传失败");
    }

    @ApiOperation(value = "下载文件")
    @GetMapping(value = "/download")
    public void download(@RequestParam("fileName") String fileName, HttpServletResponse response) {
        MinIoUtil.download(minIoProperties.getBucketName(), fileName, response);
    }

    @ApiOperation(value = "删除文件")
    @GetMapping(value = "/delete")
    public Result delete(@RequestParam("fileName") String fileName) {
        MinIoUtil.deleteFile(minIoProperties.getBucketName(), fileName);
        return Result.success(null,"删除成功");
    }

}
