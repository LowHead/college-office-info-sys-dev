package com.example.domain;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private String userUsername;
    private String userPassword;
    private String userMajorCategory;
    private String userPosition;
    private String userMail;
    private String userSex;
    private String userStatus;
    private Date userCreateTime;
    private Date userUpdateTime;
    private Long userUpdatePerson;
    private Date userLoginTime;
}
