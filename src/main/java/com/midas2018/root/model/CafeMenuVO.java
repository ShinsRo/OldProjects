package com.midas2018.root.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 카페 메뉴 VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CafeMenuVO {
    private int id;          //PK
    private String name;     //메뉴 이름
    private int price;       //가격
    private int content;     //메뉴 설명
    private CategoryVO category;    //분류
    private OptionData option;   //옵션 list
    private String imageURL; //이미지 경로
    private CafeMenuStatus status;
    private Long createdTime;
    private Long deletedTime;
    private Long updatedTime;
}
