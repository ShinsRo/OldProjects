package com.siotman.wos.yourpaper.domain.dto;

import lombok.*;

@Getter
@NoArgsConstructor
public class UidDto {

    private String uid;
    private Boolean isReprint;

    @Builder
    public UidDto(String uid, Boolean isReprint) {
        this.uid        = uid;
        this.isReprint  = isReprint;
    }
}
