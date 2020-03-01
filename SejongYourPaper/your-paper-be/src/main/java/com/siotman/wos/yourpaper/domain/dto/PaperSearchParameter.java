package com.siotman.wos.yourpaper.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaperSearchParameter {
    private String fieldName;
    private String value;

    private Boolean isAsc;
    private String sortBy;
    private Integer page;
    private Integer count;
}
