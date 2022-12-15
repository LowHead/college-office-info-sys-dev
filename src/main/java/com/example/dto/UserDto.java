package com.example.dto;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.annotation.Gender;
import com.example.annotation.Position;
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
public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户姓名")
    private String username;

    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("用户专业类别")
    private Long userMajorId;

    @ApiModelProperty("用户职位")
    @Position
    private String userPosition;

    @ApiModelProperty("邮箱地址")
    @Email
    private String userMail;

    @ApiModelProperty("性别")
    @Gender
    private String userSex;

    @ApiModelProperty("验证码")
    @Length(max = 5)
    private String code;
}
