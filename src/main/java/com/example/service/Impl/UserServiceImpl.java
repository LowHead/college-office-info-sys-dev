package com.example.service.Impl;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.BusinessException;
import com.example.common.Result;
import com.example.common.SystemException;
import com.example.domain.Role;
import com.example.domain.User;
import com.example.domain.UserRole;
import com.example.mapper.RoleMapper;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import com.example.util.CodeUtils;
import com.example.util.MD5Utils;
import com.example.util.RegularCheckUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;


    /**
     * 用户登录实现
     * @param username  账号
     * @param password  密码
     * @return
     * @throws SystemException
     */
    @Override
    public Result login(String username, String password) throws SystemException {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username",username));
        if (user == null){
            return new Result(CodeUtils.failure, null,"账户不存在！");
        }
        user = userMapper.selectOne(new QueryWrapper<User>().eq("username",username)
                .eq("password",password));
        if (user == null){
            return new Result(CodeUtils.failure,null,"账号或密码错误！");
        }
        StpUtil.login(user.getUserId());

        //将登录对象写入Session
        StpUtil.getSession().set("user",user);
        return new Result(CodeUtils.success,user,"登陆成功！");
    }

    /**
     * 用户注册实现
     * @param user
     * @return
     */
    @Override
    public Result register(User user) {
        User s = userMapper.selectOne(new QueryWrapper<User>().eq("username",user.getUsername()));
        if (user == null){
            return new Result(CodeUtils.failure,null,"禁止传递空对象！");
        }
        if (s != null){
            return new Result(CodeUtils.failure,null,"用户名已存在！");
        }
        if (!RegularCheckUtils.isValidEmail(user.getUserMail())){
            return new Result(CodeUtils.failure,null,"邮箱格式错误！");
        }
        if (user.getPassword() == null){
            return new Result(CodeUtils.failure,null,"密码不可为空！");
        }
        user.setPassword(MD5Utils.code(user.getPassword()));
        userMapper.insert(user);
        if (user.getUserPosition().equals("专业负责人")){
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getUserId());
            userRole.setRoleId(2L);
            roleMapper.insert(userRole);
        }else {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getUserId());
            userRole.setRoleId(3L);
            roleMapper.insert(userRole);
        }
        return new Result(CodeUtils.success,user,"注册成功！");
    }
}
