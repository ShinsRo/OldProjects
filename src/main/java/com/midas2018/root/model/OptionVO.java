package com.midas2018.root.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;

/**
 * 옵션 VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionVO {
    OptionStatus name;
    int count;
    int price;
}
