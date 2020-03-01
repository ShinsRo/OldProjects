package com.siotman.wos.yourpaper.domain.json;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class ParsedAuthorJson {
    private String name;
    private String fullName;
    private List<String> address;
}
