package com.midas2018.root.model;

/**
 * 카페 메뉴 상태
 */
public enum CafeMenuStatus {
    SALE(0),
    NOTSALE(1);

    int value = 0;

    private CafeMenuStatus(int value) {
        this.value = value;
    }
}
