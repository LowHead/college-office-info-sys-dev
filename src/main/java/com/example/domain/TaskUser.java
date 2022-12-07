package com.example.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class TaskUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("任务主键id")
    private Long taskId;

    @ApiModelProperty("用户主键id")
    private Long userId;

    @ApiModelProperty("任务完成状态，1为已完成，0为未完成，2为被打回状态")
    private Integer state;

    @ApiModelProperty("任务提交时间")
    private LocalDateTime taskCommitTime;
}