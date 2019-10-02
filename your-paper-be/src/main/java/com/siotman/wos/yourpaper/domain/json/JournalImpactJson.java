package com.siotman.wos.yourpaper.domain.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JournalImpactJson {
    private String sourceTitle;
    private Map<String, String> impactFactorByYear;
    private Map<String, JcrDataJson> jcrDataByYear;
}
