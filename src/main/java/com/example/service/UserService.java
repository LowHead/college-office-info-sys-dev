package com.example.service;
import com.example.common.Result;

public interface UserService {
    Result login(String username,String password);
    Result register();
}
