package com.example.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class TaskUrl implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("任务id主键")
    private Long taskId;

    @ApiModelProperty("url的id主键")
    private Long urlId;

    @ApiModelProperty("判断位，0为领导，1为老师")
    private Integer source;

}
