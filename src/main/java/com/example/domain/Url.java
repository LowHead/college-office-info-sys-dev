package com.example.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel
public class Url implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("存储文件id主键")
    @TableId(type = IdType.ASSIGN_ID)
    private Long urlId;

    @ApiModelProperty("文件下载链接")
    private String url;

    @ApiModelProperty("文件名")
    private String filename;

    @ApiModelProperty("上传人id主键")
    private Long userId;

    @ApiModelProperty("上传时间")
    private LocalDateTime createTime;
}
