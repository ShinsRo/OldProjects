package com.siotman.batchwos.batch.domain.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.Map;

@Data
@XStreamAlias("records")
public class Record {
    private String uid;
    private RecordField title;
    private RecordList doctype;
    private RecordList authors;
    private RecordList keywords;

    @XStreamImplicit(keyFieldName = "label")
    private Map<String, RecordList> source;
    @XStreamImplicit(keyFieldName = "label")
    private Map<String, RecordList> other;
}
