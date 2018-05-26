package com.midas2018.root.model;

public enum UserStatus {
    USER(0),
    SUB_ADMIN(1),
    ADMIN(2),
    DELETE(3);

    int value = 0;

    private UserStatus(int value) {
        this.value = value;
    }
}
