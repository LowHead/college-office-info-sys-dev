package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.example.common.Result;
import com.example.domain.Admin;
import com.example.service.AdminService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Api(tags ="管理员接口")
public class AdminController {

    @Autowired
    AdminService adminService;

    /**
     * 管理员登录接口
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/login")
    public Result login(@RequestParam String username,@RequestParam String password){
        return adminService.login(username,password);
    }

    /**
     * 管理员注册
     * @param admin
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody Admin admin){
        return adminService.register(admin);
    }


    /**
     * 管理员更新个人信息
     * @param admin
     * @return
     */
    @SaCheckLogin
    @PutMapping("/update")
    public Result update_admin(@RequestBody Admin admin){
        return adminService.update_admin(admin);
    }


    /**
     * 管理员登出
     * @return
     */
    @SaCheckLogin
    @GetMapping("/logout")
    Result logout(){
        return adminService.logout();
    }
}
