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
    @TableId
    private Long roleId;

    @ApiModelProperty("从user_role进行查询")
    private String role;

    @ApiModelProperty("权限状态，0为禁用状态，1为启用状态")
    private Long role_state;
}
