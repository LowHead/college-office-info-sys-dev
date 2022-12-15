package com.example.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.Result;
import com.example.domain.Admin;
import com.example.dto.AdminDto;
import com.example.mapper.AdminMapper;
import com.example.service.AdminService;
import com.example.util.CodeUtils;
import com.example.util.MD5Utils;
import com.example.util.RegularCheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 管理员登录
     * @param username  账号
     * @param password  密码
     * @return
     */
    @Override
    public Result login(String username,String password) {
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username));
        if (admin == null){
            return new Result(CodeUtils.failure, null,"账户不存在！");
        }

        admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username)
                .eq("password",password));
        if (admin == null){
            return new Result(CodeUtils.failure,null,"账号或密码错误！");
        }
        StpUtil.login(admin.getAdminId());

        //将登录对象写入Session
        StpUtil.getSession().set("admin",admin);
        return new Result(CodeUtils.success,admin,"登陆成功！");
    }


    /**
     * 管理员注册
     * @param adminDto
     * @return
     */
    @Override
    public Result register(AdminDto adminDto) {
        Admin admin = new Admin();
        BeanUtil.copyProperties(adminDto, admin, "code");
        Admin s = adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",admin.getUsername()));
        String code = stringRedisTemplate.opsForValue().get("code");
        if (admin == null){
            return new Result(CodeUtils.failure,null,"禁止传递空对象！");
        }
        if (s != null){
            return new Result(CodeUtils.failure,null,"用户名已存在！");
        }
        if (!adminDto.getCode().equals(code)){
            return new Result(CodeUtils.failure,null,"验证码错误！");
        }
        admin.setPassword(MD5Utils.code(admin.getPassword()));
        adminMapper.insert(admin);
        return new Result(CodeUtils.success,admin,"注册成功！");
    }


    /**
     * 管理员修改信息
     * @param admin
     * @return
     */
    @Override
    public Result update_admin(Admin admin) {
        if ( admin == null){
            return new Result(CodeUtils.failure,null,"禁止传递空对象！");
        }
        adminMapper.updateById(admin);
        StpUtil.getSession().set("user",admin);
        return new Result(CodeUtils.success,null,"修改成功！");
    }


    /**
     * 管理员登出
     * @return
     */
    @Override
    public Result logout() {
        StpUtil.logout();
        return new Result(CodeUtils.success,null,"登出成功！");
    }
}
