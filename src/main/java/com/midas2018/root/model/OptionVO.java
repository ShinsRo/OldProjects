package com.midas2018.root.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 옵션 VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionVO {
    String name;
    int count;
    int price;
}
