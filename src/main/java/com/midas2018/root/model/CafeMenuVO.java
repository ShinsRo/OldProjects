package com.midas2018.root.model;

import lombok.Data;

/**
 * 카페 메뉴 VO
 */
@Data
public class CafeMenuVO {
    private int id;         //PK
    private String name;    //메뉴 이름
    private int price;      //가격
    private int content;    //메뉴 설명
    private int category;   //분류 옵션 id
    private String option;  //옵션 list
    private Long createdTime;
    private Long deletedTime;
    private Long updatedTime;
}
