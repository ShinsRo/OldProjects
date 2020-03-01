package com.siotman.wos.yourpaper.domain.json;

import lombok.*;

import java.util.Map;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class JournalImpactJson {
    private String sourceTitle;
    private Map<String, String> impactFactorByYear;
    private Map<String, JcrDataJson> jcrDataByYear;
}
