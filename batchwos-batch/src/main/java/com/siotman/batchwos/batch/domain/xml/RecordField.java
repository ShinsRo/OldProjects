package com.siotman.batchwos.batch.domain.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@Data
public class RecordField {
    private String label;
    private String value;
}
