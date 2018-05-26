package com.midas2018.root.model;

import lombok.Data;

@Data
public class UserOrderVO {
    private int id;                 //pk
    private String cafeMenuList;    //메뉴 list
    private String totalPrice;      //총 가격
    private String content;         //추가요구사항
    private OrderStatus status;     //상태
    private Long createdTime;
    private Long deletedTime;
    private Long updatedTime;
}
