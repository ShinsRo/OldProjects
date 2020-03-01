package com.siotman.wos.yourpaper.domain.dto;

import com.siotman.wos.yourpaper.domain.entity.AuthorType;
import lombok.*;

@Getter
@NoArgsConstructor
public class UidDto {

    private String uid;
    private AuthorType authorType;

    @Builder
    public UidDto(String uid, AuthorType authorType) {
        this.uid            = uid;
        this.authorType     = authorType;
    }
}
