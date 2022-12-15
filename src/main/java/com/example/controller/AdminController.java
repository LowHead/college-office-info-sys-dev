package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.example.common.Result;
import com.example.domain.Admin;
import com.example.dto.AdminDto;
import com.example.service.AdminService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
@Api(tags ="管理员接口")
@Validated
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名称", required = true),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true)
    })
    @ApiOperation("登录")
    public Result login(@RequestParam String username,@RequestParam String password){
        return adminService.login(username,password);
    }

    /**
     * 管理员注册
     * @param admin
     * @return
     */
    @PostMapping("/register")
    @ApiParam(name = "admin", value = "管理员相关信息", required = true)
    @ApiOperation("注册")
    public Result register(@RequestBody @Valid AdminDto admin){
        return adminService.register(admin);
    }


    /**
     * 管理员更新个人信息
     * @param admin
     * @return
     */
    @SaCheckLogin
    @PutMapping("/update")
    @ApiParam(name = "admin", value = "管理员相关信息", required = true)
    @ApiOperation("更新个人信息")
    public Result update_admin(@RequestBody Admin admin){
        return adminService.update_admin(admin);
    }


    /**
     * 管理员登出
     * @return
     */
    @SaCheckLogin
    @GetMapping("/logout")
    @ApiOperation("登出")
    Result logout(){
        return adminService.logout();
    }
}
