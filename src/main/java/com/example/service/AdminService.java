package com.example.service;

import com.example.common.Result;
import com.example.common.SystemException;
import com.example.domain.Admin;

public interface AdminService {
    /**
     * 管理员登录
     * @param username  账号
     * @param password  密码
     * @return
     */
    Result login(String username, String password);

    /**
     * 管理员注册
     * User对象
     * @return
     */
    Result register(Admin admin);


    /**
     * 管理员修改信息
     * @param admin
     * @return
     */
    Result update_admin(Admin admin);

    /**
     * 管理员退出登录，清除网页缓存
     * @return
     */
    Result logout();
}
