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
public class JcrDataJson {
    private List<String> category;
    private List<String> rankInCategory;
    private List<String> quartileInCategory;
}
