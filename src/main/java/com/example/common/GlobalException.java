package com.example.common;

import com.example.exception.DuplicateMajorNameException;
import com.example.exception.DuplicatePositionException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(DuplicateMajorNameException.class)
    public Result DuplicateMajorName(DuplicateMajorNameException e) {
        return Result.failure(e.getMessage());
    }

    @ExceptionHandler(DuplicatePositionException.class)
    public Result DuplicatePosition(DuplicatePositionException e) {
        return Result.failure(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result MethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, Object> errorMap = fieldErrors.stream()
                .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage));
        System.out.println(errorMap);
        return new Result(404, errorMap,"请求格式错误");
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
