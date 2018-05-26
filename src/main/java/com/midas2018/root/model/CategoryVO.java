package com.midas2018.root.model;

import lombok.Data;

/***
 * 카테고리 - 커피류, 주스류 등
 */
@Data
public class CategoryVO {
    private int id;
    private String name;
    private Long createdTime;
}
