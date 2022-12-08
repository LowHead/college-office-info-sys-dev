package com.example.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户主键id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long userId;

    @ApiModelProperty("用户姓名")
    private String username;

    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("用户专业类别")
    private Long userMajorId;

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
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("修改人主键id")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updatePerson;

    @ApiModelProperty("最新登录时间")
    private LocalDateTime loginTime;
}
