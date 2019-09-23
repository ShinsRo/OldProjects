package com.siotman.wos.domain.dto;

import com.siotman.wos.domain.jpa.Author;
import com.siotman.wos.domain.jpa.Paper;
import lombok.Data;

import java.util.List;

@Data
public class AuthorDto {
    private String name;
    private String fullName;
    private Boolean reprint;
    private List<String> addresses;

    public Author toEntity(String uid) {
        return Author.builder()
                .paper(Paper.builder().uid(uid).build())
                .name(name)
                .reprint(reprint)
                .addresses(addresses)
                .fullName(fullName)
                .build();
    }
}
