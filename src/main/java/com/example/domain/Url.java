package com.example.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel
public class Url {

    @ApiModelProperty("存储文件id主键")
    private Long urlId;

    @ApiModelProperty("文件下载链接")
    private String url;

    @ApiModelProperty("文件名")
    private String filename;

    @ApiModelProperty("上传人id主键")
    private String userId;

    @ApiModelProperty("上传时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime uploadTime;

}
