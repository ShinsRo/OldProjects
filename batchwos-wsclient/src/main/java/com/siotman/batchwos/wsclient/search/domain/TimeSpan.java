package com.siotman.batchwos.wsclient.search.domain;


import lombok.Builder;
        import lombok.Data;

        import javax.xml.soap.SOAPElement;
        import javax.xml.soap.SOAPException;

@Data
@Builder
public class TimeSpan implements Parameters {
    private String begin;
    private String end;

    @Override
    public SOAPElement insertElementIn(SOAPElement parent) throws SOAPException {
        if (begin == null && end == null) return null;

        SOAPElement timeSpan = parent.addChildElement("timeSpan");
        if (begin != null)  timeSpan.addChildElement("begin").setValue(begin);
        if (end != null)    timeSpan.addChildElement("end").setValue(end);
        return timeSpan;
    }
}