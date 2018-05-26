package com.midas2018.root.model;

import lombok.Data;

/**
 * 사용자 VO
 */
@Data
public class UserVO {
    private int id;
    private String name;
    private String email;
    private String password;
    private String department;
    private String subDepartment;
    private UserStatus status;
    private Long createdTime;
    private Long deletedTime;
    private Long updatedTime;
}
