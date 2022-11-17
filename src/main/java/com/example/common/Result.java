package com.example.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@ApiModel
public class Result implements Serializable {

    @ApiModelProperty("状态码")
    private int code;

    @ApiModelProperty("实体类对象")
    private Object data;

    @ApiModelProperty("提示信息")
    private String msg;

    public static Result success(Object object,String msg){
        return new Result(200,object,msg);
    }

    public static Result failure(String msg){
        return new Result(500,null,msg);
    }
}
