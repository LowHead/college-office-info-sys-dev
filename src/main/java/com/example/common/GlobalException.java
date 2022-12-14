package com.example.common;

import com.example.exception.DuplicateMajorNameException;
import com.example.exception.DuplicatePositionException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalException {

    @ExceptionHandler(DuplicateMajorNameException.class)
    public Result DuplicateMajorName(DuplicateMajorNameException e) {
        return Result.failure(e.getMessage());
    }

    @ExceptionHandler(DuplicatePositionException.class)
    public Result DuplicatePosition(DuplicatePositionException e) {
        return Result.failure(e.getMessage());
    }

    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException e){
        //记录日志
        //发送消息给运维
        //发送消息给开发人员
        System.out.println(e.getMessage());
        return new Result(e.getCode(),null,e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result doBusinessException(BusinessException e){
        return new Result(e.getCode(),null,e.getMessage());
    }

    //处理其他异常
    @ExceptionHandler(Exception.class)
    public Result doException(Exception e){
        System.out.println(e.getMessage());
        return new Result(401,null,e.getMessage());
    }

}
