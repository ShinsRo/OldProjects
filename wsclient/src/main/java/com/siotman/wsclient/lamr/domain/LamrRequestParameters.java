package com.siotman.wsclient.lamr.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class LamrRequestParameters {
    private String mapName;
    private Map<String, String> map;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb  .append("<map name=\"").append(mapName).append("\">");

        Iterator<String> iter = map.keySet().iterator();
        while (iter.hasNext()) {
            String valName = iter.next();
            String value = map.get(valName);
            sb  .append("<val name=\"").append(valName).append("\">")
                    .append(value)
                .append("</val>");
        }

        sb  .append("</map>");
        return sb.toString();
    }
}
