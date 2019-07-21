package com.siotman.batchwos.batch.domain.xml;

import lombok.Data;

import java.util.List;

@Data
public class XmlListField {
    private String label;
    private List<String> value;
}
