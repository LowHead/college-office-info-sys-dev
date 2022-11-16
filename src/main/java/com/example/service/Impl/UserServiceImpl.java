package com.example.service.Impl;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.Result;
import com.example.domain.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public Result login(String username, String password) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username",username)
                .eq("password",password));
        if (user != null){
            return new Result(200,user,"登陆成功！");
        }
//        StpUtil.login();
        return null;
    }

    @Override
    public Result register() {
        return null;
    }
}
