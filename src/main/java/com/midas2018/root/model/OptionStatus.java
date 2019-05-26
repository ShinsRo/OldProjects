package com.midas2018.root.model;

/**
 * 옵션
 */
public enum OptionStatus implements ValueEnum {
    SHOT(0),
    VANILA_SYRUP(1),
    CARAMEL_SYRUP(2),
    HAZELNUT_SYRUP(3),
    LOW_FAT(4),
    WHIPPED_CREAM(5);

    int value = 0;

    private OptionStatus(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
