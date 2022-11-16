package com.example.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("权限表主键id")
    private Long roleId;

    @ApiModelProperty("用户表主键id")
    private Long userId;
}
