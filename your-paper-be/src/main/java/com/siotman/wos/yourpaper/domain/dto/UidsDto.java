package com.siotman.wos.yourpaper.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UidsDto {

    private String username;
    private List<UidDto> uids;

    @Builder
    public UidsDto(String username, List<UidDto> uids) {
        this.username   = username;
        this.uids       = uids;
    }

}