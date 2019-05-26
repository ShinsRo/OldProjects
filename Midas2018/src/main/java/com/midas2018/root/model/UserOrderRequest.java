package com.midas2018.root.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserOrderRequest {
    @NotNull
    private String cafeMenuList;    //메뉴 list
    private String totalPrice;      //총 가격
    private String content;         //추가요구사항
    private OrderStatus status;     //상태
}
