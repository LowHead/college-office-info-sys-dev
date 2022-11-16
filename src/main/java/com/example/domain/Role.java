package com.example.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class Role {

    @ApiModelProperty("权限主键id")
    private Long roleId;

    @ApiModelProperty("权限，根据user_role进行查询")
    private Long role;

    @ApiModelProperty("权限状态，0为禁用状态，1为启用状态")
    private Integer role_state;

}