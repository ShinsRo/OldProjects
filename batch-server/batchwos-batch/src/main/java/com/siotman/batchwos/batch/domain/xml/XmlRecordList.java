package com.siotman.batchwos.batch.domain.xml;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.List;

@Data
public class XmlRecordList {
    private String label;
    @XStreamImplicit
    private List<String> value;
}
