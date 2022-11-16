package com.example.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class TaskConfirmation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("任务主键id")
    private Long taskId;

    @ApiModelProperty("用户主键id")
    private Long userId;
}
