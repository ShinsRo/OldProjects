package com.siotman.wos.yourpaper.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UidsDto {

    private String username;
    private List<UidDto> uids;

}
