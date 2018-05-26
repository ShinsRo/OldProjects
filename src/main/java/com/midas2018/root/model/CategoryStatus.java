package com.midas2018.root.model;

/***
 * 카테고리
 */
public enum CategoryStatus implements ValueEnum {
    COFFEE(0),
    NONCOFFEE(1),
    ETC(2);

    int value = 0;

    private CategoryStatus(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
