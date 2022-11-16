package com.example.config;


import com.example.common.BusinessException;
import com.example.common.Result;
import com.example.common.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//异常处理器
@RestControllerAdvice
public class ProjectExceptionAdvice {
    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException e){
        //记录日志
        //发送消息给运维
        //发送消息给开发人员
        System.out.println(e.getMessage());
        return new Result(null,e.getCode(),e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result doBusinessException(BusinessException e){
        System.out.println(e.getMessage());
        return new Result(null,e.getCode(),e.getMessage());
    }

    //处理其他异常
    @ExceptionHandler(Exception.class)
    public Result doException(Exception e){
        System.out.println(e.getMessage());
        return new Result(null,401,e.getMessage());
    }
}
