package com.example.dto;

import com.example.annotation.Gender;
import com.example.annotation.Position;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class MajorDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("专业名称")
    private String majorName;

    @ApiModelProperty("用户姓名")
    private List<String> username;

    @ApiModelProperty("用户职位")
    private List<String> userPosition;

    @ApiModelProperty("邮箱地址")
    private List<String> userMail;

    @ApiModelProperty("性别")
    private List<String> userSex;

}
