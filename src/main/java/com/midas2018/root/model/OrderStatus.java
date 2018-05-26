package com.midas2018.root.model;

/**
 * 주문 상태
 */
public enum OrderStatus {
    WAITING(0),   //대기
    MAKING(1),    //만드는중
    COMPLELTE(2); //완료

    int value = 0;

    private OrderStatus(int value) {
        this.value = value;
    }
}
