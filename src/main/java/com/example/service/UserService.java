package com.example.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.Result;
import com.example.common.SystemException;
import com.example.domain.User;
import com.example.dto.UserDto;

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
    Result register(UserDto user);


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
    List<User> getUserByIds(List<Long> Ids);

    /**
     * 根据用户id查询用户数据
     * @param id 用户主键id
     * @return 用户数据
     */
    User getUserById(Long id);
}
