package com.siotman.wos.yourpaper.domain.json;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class JcrDataJson {
    private List<String> category;
    private List<String> rankInCategory;
    private List<String> quartileInCategory;
}
