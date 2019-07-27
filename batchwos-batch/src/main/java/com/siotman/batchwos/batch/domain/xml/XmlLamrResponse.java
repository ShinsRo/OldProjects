package com.siotman.batchwos.batch.domain.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@XStreamAlias("response")
public class XmlLamrResponse {
    private List<XmlLamrResponse> map;

    @XStreamAsAttribute
    private String name;

    private Fn fn;

    @Data
    @XStreamAlias("fn")
    public class Fn {
        @XStreamAsAttribute
        private String name;
        @XStreamAsAttribute
        private String rc;

        private Map map;
    }

    @Data
    @XStreamAlias("map")
    public class Map {
        @XStreamAsAttribute
        private String name;

        @XStreamImplicit(keyFieldName = "name")
        private java.util.Map<String, Map> map;
        @XStreamImplicit(keyFieldName = "name")
        private java.util.Map<String, Val> val;

    }

    @Data
    @XStreamAlias("val")
    @XStreamConverter(value= ToAttributedValueConverter.class, strings={"value"})
    public class Val {
        private String name;
        private String value;
    }
}
