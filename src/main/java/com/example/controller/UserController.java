package com.example.controller;

import com.example.common.Result;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping ("/login")
    public Result login(@RequestParam String username, @RequestParam String password){
        return userService.login(username,password);
    }
}
