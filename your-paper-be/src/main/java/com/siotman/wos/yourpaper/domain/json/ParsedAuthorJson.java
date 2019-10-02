package com.siotman.wos.yourpaper.domain.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParsedAuthorJson {
    private String name;
    private String fullName;
    private List<String> address;
}
