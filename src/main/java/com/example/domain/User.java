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
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel
@Validated
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户主键id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long userId;

    @ApiModelProperty("用户姓名")
    @NotBlank
    private String username;

    @ApiModelProperty("用户密码")
    @NotBlank
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

    @ApiModelProperty("在职状态，0离职，1在职")
    private String userStatus;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("修改人主键id")
    private Long updatePerson;

    @ApiModelProperty("最新登录时间")
    private LocalDateTime loginTime;
}
