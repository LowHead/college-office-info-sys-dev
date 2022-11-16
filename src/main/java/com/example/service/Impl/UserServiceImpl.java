package com.example.service.Impl;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.BusinessException;
import com.example.common.Result;
import com.example.common.SystemException;
import com.example.domain.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import com.example.util.MD5Utils;
import com.example.util.RegularCheckUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

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

        //将登录对象写入Session
        StpUtil.getSession().set("user",user);
        return new Result(200,user,"登陆成功！");
    }

    @Override
    public Result register(User user) {
        if (user == null){
            return new Result(404,null,"禁止传递空对象！");
        }
        if (!RegularCheckUtils.isValidEmail(user.getUserMail())){
            return new Result(404,null,"邮箱格式错误！");
        }
        if (user.getPassword() == null){
            return new Result(404,null,"密码不可为空！");
        }
        user.setPassword(MD5Utils.code(user.getPassword()));
//        StpUtil.login(user.getUserId());
        userMapper.insert(user);
        return new Result(200,user,"注册成功！");
    }
}
