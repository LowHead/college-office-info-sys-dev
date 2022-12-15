package com.example.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.annotation.Gender;
import com.example.annotation.Position;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Data
@ApiModel
@Validated
public class Admin {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("管理员主键id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long adminId;

    @ApiModelProperty("管理员姓名")
    private String username;

    @ApiModelProperty("管理员密码")
    private String password;

    @ApiModelProperty("管理员职位")
    private String adminPosition;

    @ApiModelProperty("邮箱地址")
    @Email
    private String adminMail;

    @ApiModelProperty("性别")
    @Gender
    private String adminSex;

    @ApiModelProperty("在职状态，0离职，1在职")
    private String adminStatus;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("最新登录时间")
    private LocalDateTime loginTime;
}
