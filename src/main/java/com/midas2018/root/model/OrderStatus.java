package com.midas2018.root.model;

/**
 * 주문 상태
 */
public enum OrderStatus implements ValueEnum {
    WAITING(0),   //대기
    MAKING(1),    //만드는중
    COMPLETE(2); //완료

    int value = 0;

    private OrderStatus(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
