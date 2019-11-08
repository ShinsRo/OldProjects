package com.siotman.wos.yourpaper.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberPaperQueryParameters {
    private String username;

    private String sortBy;
    private Boolean isAsc;
    private Integer firstRecord;
    private Integer count;
}
