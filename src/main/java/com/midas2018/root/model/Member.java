package com.midas2018.root.model;
/*
최근 수정일 : 2018.05.22
작성자 : 김승신
설명 : 사용자 정보 VO
*/
import lombok.Data;

@Data
public class Member {
    String email;
    String password;
    String name;

    public Member(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }
}
