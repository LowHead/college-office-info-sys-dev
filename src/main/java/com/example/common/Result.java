package com.example.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Result implements Serializable {
    private Object data;
    private int code;
    private String msg;

    public static Result success(Object object,String s){
        return new Result(object,200,s);
    }

    public static Result failure(String s){
        return new Result(null,500,s);
    }
}
