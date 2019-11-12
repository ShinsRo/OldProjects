package com.siotman.wos.jaxws2rest.domain.dto;

import com.thomsonreuters.wokmws.v3.woksearchlite.LabelValuesPair;
import com.thomsonreuters.wokmws.v3.woksearchlite.LiteRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiteRecordDto {
    private String uid;
    private String doi;
    private String title;

    private Map<String, String> identifier;
    private Map<String, String> source;

    private Map<String, List<String>> other;

    private List<String> authors;
    private List<String> doctype;
    private List<String> keywords;

    public LiteRecordDto(LiteRecord record) {
        this.uid    = record.getUid();
        this.title  = record
                .getTitle().get(0)
                .getValue().get(0);

        this.identifier = new HashMap<>();
        this.other      = new HashMap<>();
        _extractOtherAndSet(record.getOther());

        this.source     = new HashMap<>();
        _extractSourceAndSet(record.getSource());

        this.authors    = _peelOff(record.getAuthors());
        this.doctype    = _peelOff(record.getDoctype());
        this.keywords   = _peelOff(record.getKeywords());
    }

    private void _extractOtherAndSet(List<LabelValuesPair> other) {
        String label;
        String key;
        List<String> values;
        String value;

        for (LabelValuesPair p : other) {
            label   = p.getLabel();
            values  = p.getValue();
            value   = values.get(0);
            switch (label) {
                case "Identifier.Doi":
                    this.doi = value;
                    break;

                case "Identifier.Ids":
                case "Identifier.Eissn":
                case "Identifier.Issn":
                case "Identifier.Isbn":
                    key = label.split("\\.")[1];
                    key = key.toLowerCase();
                    this.identifier.put(key, value);
                    break;

                case "Identifier.Xref_Doi":
                    if (this.doi == null) this.doi = value;
                case "Identifier.article_no":
                case "ResearcherID.Disclaimer":
                case "Contributor.ResearcherID.Names":
                case "Contributor.ResearcherID.ResearcherIDs":
                    this.other.put(label, values);
                    break;

                default: break;
//                default: logger.warn(field + "는 알 수 없는 필드입니다. (<other>)");
            }
        }
    }

    private void _extractSourceAndSet(List<LabelValuesPair> source) {
        String label;
        String key;
        List<String> values;
        String value;

        for (LabelValuesPair p : source) {
            label   = p.getLabel();
            values  = p.getValue();
            value   = values.get(0);
            switch (label) {
                case "Issue":
                case "Pages":
                case "Volume":
                case "SourceTitle":
                    key = Character.toLowerCase(label.charAt(0)) + label.substring(1);
                    this.source.put(key, value);
                    break;

                case "Published.BiblioDate":
                    key = "publishedDate";
                    this.source.put(key, value);
                    break;

                case "Published.BiblioYear":
                    key = "publishedYear";
                    this.source.put(key, value);
                    break;

                case "BookSeriesTitle":
                case "SupplementNumber":
                case "SpecialIssue":
                    this.other.put(label, values);
                    break;

                default: break;
//                default: logger.warn(field + "는 알 수 없는 필드입니다. (<source>)");
            }
        }
    }


    private List<String> _peelOff(List<LabelValuesPair> pairs) {
        if (pairs == null || pairs.size() == 0) {
            return new ArrayList<>();
        }

        List<String> value = pairs.get(0).getValue();
        return value;
    }
}
