package com.example.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel
public class TaskDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("任务标题")
    private String taskTitle;

    @ApiModelProperty("任务内容")
    private String taskContent;

    @ApiModelProperty("任务等级")
    private String taskRank;

    @ApiModelProperty("发布类型，通知与公告，0表示通知，1表示公告")
    private String taskType;

    @ApiModelProperty("截止时间")
    private LocalDateTime deadTime;
}
