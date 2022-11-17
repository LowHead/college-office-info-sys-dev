package com.example.controller;

import com.example.common.Result;
import com.example.common.SystemException;
import com.example.domain.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     * @throws SystemException
     */
    @GetMapping ("/login")
    public Result login(@RequestParam String username, @RequestParam String password) throws SystemException {
        return userService.login(username,password);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        return userService.register(user);
    }

    @PutMapping("/update")
    public Result update_user(@RequestBody User user){
        return userService.update_user(user);
    }
}
