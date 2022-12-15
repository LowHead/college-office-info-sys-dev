package com.example.controller;

import com.example.common.Result;
import com.example.common.SystemException;
import com.example.domain.User;
import com.example.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Api(tags = "用户接口")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     * @throws SystemException
     */
    @GetMapping ("/login")
    @ApiOperation("用户登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true),
    })
    public Result login(@RequestParam String username, @RequestParam String password) throws SystemException {
        return userService.login(username,password);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("用户注册接口")
    public Result register(@RequestBody User user){
        return userService.register(user);
    }

    /**
     * 用户修改信息接口
     * @param user
     * @return
     */
    @PutMapping("/update")
    @ApiOperation("用户修改信息接口")
    public Result update_user(@RequestBody User user){
        return userService.update_user(user);
    }


    /**
     * 用户登出接口
     * @return
     */
    @GetMapping("/logout")
    @ApiOperation("用户登出")
    public Result logout(){
        return userService.logout();
    }
}
