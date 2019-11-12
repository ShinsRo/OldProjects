package com.siotman.wos.jaxws2rest.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LamrResultsDto {
    private String uid;
    private String doi;
    private String pmid;
    private String timesCited;
    private String sourceURL;
    private String citingArticlesURL;
    private String relatedRecordsURL;

    public void setByName(String name, String value) {
        if (value == null) value = "";
        switch (name) {
            case "ut":                  this.uid                = "WOS:" + value; break;
            case "doi":                 this.doi                = (value != null)? value:""; break;
            case "pmid":                this.pmid               = (value != null)? value:""; break;
            case "timesCited":          this.timesCited         = (value != null)? value:"0"; break;
            case "sourceURL":           this.sourceURL          = (value != null)? value:""; break;
            case "citingArticlesURL":   this.citingArticlesURL  = (value != null)? value:""; break;
            case "relatedRecordsURL":   this.relatedRecordsURL  = (value != null)? value:""; break;
            default: break;
        }
    }
}
