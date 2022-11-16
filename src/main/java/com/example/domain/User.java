package com.example.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户主键id")
    private Long userId;

    @ApiModelProperty("用户姓名")
    private String username;

    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("用户专业类别")
    private String userMajorCategory;

    @ApiModelProperty("用户职位")
    private String userPosition;

    @ApiModelProperty("邮箱地址")
    private String userMail;

    @ApiModelProperty("性别")
    private String userSex;

    @ApiModelProperty("在职状态，0离职，1在职")
    private String userStatus;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime CreateTime;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime UpdateTime;

    @ApiModelProperty("修改人主键id")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long UpdatePerson;

    @ApiModelProperty("最新登录时间")
    private LocalDateTime LoginTime;
}
