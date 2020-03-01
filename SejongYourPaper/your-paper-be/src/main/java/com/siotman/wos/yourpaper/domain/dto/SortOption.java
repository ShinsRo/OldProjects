package com.siotman.wos.yourpaper.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SortOption {
    private String sortBy;
    private Boolean isAsc;
}
