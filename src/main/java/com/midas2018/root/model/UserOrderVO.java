package com.midas2018.root.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrderVO {
    private Integer id;                 //pk
    private CafeMenuAndOptionsList cafeMenuList;    //메뉴 list
    private String totalPrice;      //총 가격
    private String content;         //추가요구사항
    private OrderStatus status;     //상태
    private Long createdTime;
    private Long deletedTime;
    private Long updatedTime;
}
