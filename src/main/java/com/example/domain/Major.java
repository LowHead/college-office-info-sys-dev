package com.example.domain;

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
public class Major implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty("专业名称主键id")
    private Long majorId;

    @ApiModelProperty("专业名称")
    private String majorName;

    @ApiModelProperty("创建人主键id")
    private Long createUserId;

    @ApiModelProperty("更新人主键id")
    private Long updateUserId;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
