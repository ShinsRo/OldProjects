package com.midas2018.root.model;

import lombok.Data;

@Data
public class CafeMenuBean {
    private int id;          //PK
    private String name;     //메뉴 이름
    private int price;       //가격
    private String content;     //메뉴 설명
    private String category;    //분류
    private String option;   //옵션 list
    private String imageURL; //이미지 경로
}
