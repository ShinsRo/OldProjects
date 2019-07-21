package com.siotman.batchwos.batch.domain.xml;

import lombok.Data;

import java.util.List;

@Data
public class XmlRecord {
    String uid;
    List<XmlListField> xmlListFields;
    List<XmlStringField> xmlStringFields;
}
