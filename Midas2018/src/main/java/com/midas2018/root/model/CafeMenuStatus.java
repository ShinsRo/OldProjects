package com.midas2018.root.model;

/**
 * 카페 메뉴 상태
 */
public enum CafeMenuStatus implements ValueEnum{
    SALE(0),
    NOTSALE(1);

    int value = 0;

    private CafeMenuStatus(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
