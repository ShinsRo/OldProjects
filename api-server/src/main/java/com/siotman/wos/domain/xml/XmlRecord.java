package com.siotman.wos.domain.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Data
@XStreamAlias("records")
public class XmlRecord {
    private String uid;
    private XmlRecordField title;
    private XmlRecordList doctype;
    private XmlRecordList keywords;

    @XStreamImplicit(keyFieldName = "label")
    private Map<String, XmlRecordList> authors;
    @XStreamImplicit(keyFieldName = "label")
    private Map<String, XmlRecordList> source;
    @XStreamImplicit(keyFieldName = "label")
    private Map<String, XmlRecordList> other;

    public String getTitle() {
        return title.getValue();
    }
    public List<String> getDoctype() {
        if (doctype == null) return null;
        return doctype.getValue();
    }
    public List<String> getKeywords() {
        if (keywords == null) return null;
        return keywords.getValue();
    }
    public List<String> getAuthors() {
        Iterator<String> iter = authors.keySet().iterator();
        List ret = new ArrayList();
        while(iter.hasNext()) {
            String key = iter.next();
            XmlRecordList list = authors.get(key);
            ret.addAll(list.getValue());
        }
        return ret;
    }
}
