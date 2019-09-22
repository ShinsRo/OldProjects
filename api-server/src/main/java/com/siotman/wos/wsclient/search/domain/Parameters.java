package com.siotman.wos.wsclient.search.domain;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

public interface Parameters {
    SOAPElement insertElementIn(SOAPElement parent) throws SOAPException;
}
