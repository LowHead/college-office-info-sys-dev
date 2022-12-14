package com.example.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Future;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("任务主键")
    @TableId
    private Long taskId;

    @ApiModelProperty("任务标题")
    private String taskTitle;

    @ApiModelProperty("任务内容")
    private String taskContent;

    @ApiModelProperty("任务等级")
    private String taskRank;

    @ApiModelProperty("任务状态，0表示任务已过期，1表示任务未过期")
    @Range(max = 1, min = 0)
    private Integer taskStatus;

    @ApiModelProperty("发布类型，通知与公告，0表示通知，1表示公告")
    private Integer taskType;

    @ApiModelProperty("排序，数字小在前")
    private Integer taskSort;

    @ApiModelProperty("创建人id")
    private Long createPerson;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("截止时间")
    @Future
    private LocalDateTime deadTime;

}
