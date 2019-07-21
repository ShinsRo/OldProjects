package com.siotman.batchwos.wsclient.lamr.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LamrRequestParameters {
    private String mapName;
    private String valName;
    private String value;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb  .append("<map name=\"").append(mapName).append("\">")
                .append("<val name=\"").append(valName).append("\">")
                    .append(value)
                .append("</val>")
            .append("</map>");
        return sb.toString();
    }
}
