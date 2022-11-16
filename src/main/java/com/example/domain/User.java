package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
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
