package com.example.service.Impl;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.Result;
import com.example.common.SystemException;
import com.example.domain.User;
import com.example.domain.UserRole;
import com.example.mapper.UserMapper;
import com.example.mapper.UserRoleMapper;
import com.example.service.UserService;
import com.example.util.CodeUtils;
import com.example.util.MD5Utils;
import com.example.util.RegularCheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService, StpInterface {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

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

//        //MD5加密 后面需要去掉，让前端进行对密码加密,保证密码安全性
//        password = MD5Utils.code(password);

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
        return new Result(CodeUtils.success,user,"注册成功！");
    }

    /**
     * 用户修改信息实现
     * @param user
     * @return
     */
    @Override
    public Result update_user(User user) {
        if ( user == null){
            return new Result(CodeUtils.failure,null,"禁止传递空对象！");
        }
        userMapper.updateById(user);
        StpUtil.getSession().set("user",user);
        return new Result(CodeUtils.success,null,"修改成功！");
    }


    /**
     * 登出功能
     * @return
     */
    @Override
    public Result logout() {
        StpUtil.logout();
        return new Result(CodeUtils.success,null,"登出成功！");
    }

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> list = new ArrayList<String>();
        // 本list仅做模拟，实际项目中要根据具体业务逻辑来查询权限
        Long id = (Long)loginId;
        User user = userMapper.selectById(id);
        if (user == null){
            list.add("admin.post");
            list.add("admin.update");
            list.add("admin.get");
            list.add("admin.delete");
            return list;
        }

        list.add("user.post");
        list.add("user.update");
        list.add("user.get");
        list.add("user.delete");
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 本list仅做模拟，实际项目中要根据具体业务逻辑来查询角色
        Long id = (Long)loginId;
        User user = userMapper.selectById(id);
        List<String> list = new ArrayList<String>();

        if (user == null){
            list.add("admin");
        }
        list.add("user");
        return list;
    }
}
