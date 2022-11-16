package com.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("权限主键id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long roleId;

    @ApiModelProperty("权限，根据user_role进行查询")
    private Long role;

    @ApiModelProperty("权限状态，0为禁用状态，1为启用状态")
    private Integer role_state;

}
