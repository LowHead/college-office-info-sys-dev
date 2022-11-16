package com.example.service;
import com.example.common.Result;
import com.example.common.SystemException;

public interface UserService {
    Result login(String username,String password) throws SystemException;
    Result register();
}
