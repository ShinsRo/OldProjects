package com.midas2018.root.model;

/**
 * 사용자 상태
 */
public enum UserStatus implements ValueEnum{
    USER(0),
    SUB_ADMIN(1),
    ADMIN(2),
    DELETE(3);

    int value = 0;

    private UserStatus(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
