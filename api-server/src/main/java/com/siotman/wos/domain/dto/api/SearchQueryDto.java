package com.siotman.wos.domain.dto.api;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchQueryDto {
    private String type;
    private String query;
//    private LocalDateTime timeSpanStart;
//    private LocalDateTime timeSpanEnd;
}
