package com.midas2018.root.model;

public enum CafeMenuStatus {
    SALE(0),
    NOTSALE(1);

    int value = 0;

    private CafeMenuStatus(int value) {
        this.value = value;
    }
}
