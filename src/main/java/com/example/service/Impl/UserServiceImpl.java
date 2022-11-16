package com.example.service.Impl;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.BusinessException;
import com.example.common.Result;
import com.example.common.SystemException;
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
    public Result login(String username, String password) throws SystemException {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username",username));
        if (user == null){
            return new Result(401, null,"账户不存在！");
        }
        user = userMapper.selectOne(new QueryWrapper<User>().eq("username",username)
                .eq("password",password));
        if (user == null){
            return new Result(401,null,"账号或密码错误！");
        }
        StpUtil.login(user.getUserId());
        System.out.println("token: "+StpUtil.getLoginId());
        return new Result(200,user,"登陆成功！");
    }

    @Override
    public Result register() {
        return null;
    }
}
