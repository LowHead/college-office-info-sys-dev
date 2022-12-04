package com.example.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.Result;
import com.example.common.SystemException;
import com.example.domain.User;

import java.util.List;

public interface UserService extends IService<User> {

    /**
     * 用户登录
     * @param username  账号
     * @param password  密码
     * @return
     * @throws SystemException
     */
    Result login(String username,String password) throws SystemException;

    /**
     * 用户注册
     * User对象
     * @return
     */
    Result register(User user);


    /**
     * 用户修改信息
     * @param user
     * @return
     */
    Result update_user(User user);

    /**
     * 退出登录，清除网页缓存
     * @return
     */
    Result logout();


    /**
     * 根据id来查询数据
     * @return
     */
    List<User> getUserById(List<Long> Ids);
}
