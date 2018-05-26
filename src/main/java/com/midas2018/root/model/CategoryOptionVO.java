package com.midas2018.root.model;

import lombok.Data;

/***
 * 카테고리 분류 (커피류, 주스류)에 따른 옵션 VO
 */
@Data
public class CategoryOptionVO {
    private int id;             //PK
    private String name;        //옵션 list
    private Long createdTime;
}
