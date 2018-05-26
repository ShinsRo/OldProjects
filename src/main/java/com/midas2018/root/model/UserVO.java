package com.midas2018.root.model;

import lombok.Data;

@Data
public class UserVO {
    private int id;
    private String name;
    private String email;
    private String password;
    private String department;
    private String subDepartment;
    private Long createdTime;
    private Long deletedTime;
    private Long updatedTime;
    private UserStatus status;
}
