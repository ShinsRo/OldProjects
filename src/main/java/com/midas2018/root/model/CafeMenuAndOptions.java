package com.midas2018.root.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CafeMenuAndOptions {
    private Integer cafeMenuId;
    private List<OptionVO> optionList;
}
