package com.example.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Result implements Serializable {

    private int code;
    private Object data;
    private String msg;

    public static Result success(Object object,String msg){
        return new Result(200,object,msg);
    }

    public static Result failure(String msg){
        return new Result(500,null,msg);
    }
}
