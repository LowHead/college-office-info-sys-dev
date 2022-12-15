package com.example.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.annotation.Gender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel
public class AdminDto implements Serializable {
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

    @ApiModelProperty("验证码")
    @Length(max = 5)
    private String code;

}
