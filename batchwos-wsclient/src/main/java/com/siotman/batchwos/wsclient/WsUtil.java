package com.siotman.batchwos.wsclient;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.PrintStream;

public class WsUtil {
    /**
     * 메세지 프린팅/로깅 메서드
     *
     * @param soapMessage
     * @param prepend
     * @param stream
     * @throws SOAPException
     * @throws TransformerException
     */
    public static void printSOAPMessage(SOAPMessage soapMessage, String prepend, PrintStream stream) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            Source sourceContent = soapMessage.getSOAPPart().getContent();

            stream.println(prepend);
            StreamResult result = new StreamResult(stream);
            transformer.transform(sourceContent, result);
            stream.println("\n:END");
        } catch (TransformerException | SOAPException e) {
            e.printStackTrace();
        }
    }
}
