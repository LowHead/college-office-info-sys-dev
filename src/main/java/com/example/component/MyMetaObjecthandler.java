package com.example.component;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.domain.User;
import com.sun.prism.impl.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自定义元数据对象处理器
 * 为实体类自动配置时间以及创建者和更新者
 * 需要在需要的字段加上注解
 * 创建字段使用 @TableField(fill = FieldFill.INSERT)
 * 更新字段使用 @TableField(fill = FieldFill.INSERT_UPDATE)
 */

@Component
@Slf4j
public class MyMetaObjecthandler implements MetaObjectHandler {
//    protected User user = (User) StpUtil.getSession().get("user");

    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", StpUtil.getLoginId());
        metaObject.setValue("UpdatePerson", StpUtil.getLoginId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("UpdatePerson", StpUtil.getLoginId());
    }
}
