package com.siotman.batchwos.wsclient.search;

import com.siotman.batchwos.wsclient.search.domain.RetrieveParameters;
import com.siotman.batchwos.wsclient.search.domain.SearchQueryParameters;

import javax.xml.soap.*;

public class SearchMessageGen {
    private final String NS_WOKSEARCH = "http://woksearchlite.v3.wokmws.thomsonreuters.com";


    private SearchMessageGen() {}
    private static SearchMessageGen instance;

    public static synchronized SearchMessageGen getInstance() {
        if (instance == null) {
            instance = new SearchMessageGen();
        }
        return instance;
    }

    /**
     * 검색 요청 SOAP 메세지 제작
     *
     *
     * Constructed SOAP Request Message:
     *  <SOAP-ENV:Envelope  xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
     *                      xmlns:job="http://auth.cxf.wokmws.thomsonreuters.com">
     *      <SOAP-ENV:Header/>
     *      <SOAP-ENV:Body>
     *          <job:search>
     *
     *              <queryParameters>
     *                  {...searchQueryParameters}
     *              </queryParameters>
     *
     *              <retrieveParameters>
     *                  {...retrieveParameters}
     *              </retrieveParameters>
     *          </ns2:retrieve>
     *      </SOAP-ENV:Body>
     *  </SOAP-ENV:Envelope>
     *
     * @param SID
     * @param searchQueryParameters
     * @param retrieveParameters
     * @return
     * @throws SOAPException
     */
    public SOAPMessage search(String SID, SearchQueryParameters searchQueryParameters, RetrieveParameters retrieveParameters)
    throws SOAPException {
        SOAPMessage soapMessage = newSOAPMessage();

        // SOAP Body
        SOAPBody soapBody = soapMessage.getSOAPPart().getEnvelope().getBody();
        SOAPElement searchElement = soapBody.addChildElement("search", "job");

        // Headers
        MimeHeaders headers = soapMessage.getMimeHeaders();

        String cookie = String.format("SID=%s", SID);
        headers.addHeader("Cookie", cookie);

        if (searchQueryParameters != null)  searchQueryParameters.insertElementIn(searchElement);
        if (retrieveParameters != null)     retrieveParameters.insertElementIn(searchElement);

        soapMessage.saveChanges();

        return soapMessage;
    }

    /**
     * 취득 요청 SOAP 메세지 제작
     *
     *
     * 이전 search 혹은 retrieveById 요청에 따라 얻는 queryId를 제출한다.
     * 이때 다른 취득 파라미터를 사용해 출력값을 다르게 할 수 있다. (ex. 정렬 변경)
     * 또 쿼리 당 100개 레코드 취득 제한을 이 요청으로 해결할 수 있다.
     * 연속적인 retrieve 요청으로 얻은 100개 이후 레코드를 취득한다.
     *
     * Constructed SOAP Request Message:
     *  <SOAP-ENV:Envelope  xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
     *                      xmlns:job="http://auth.cxf.wokmws.thomsonreuters.com">
     *      <SOAP-ENV:Header/>
     *      <SOAP-ENV:Body>
     *          <job:retrieve>
     *
     *              <queryId>{queryId}</queryId>
     *
     *              <retrieveParameters>
     *                  <firstRecord>{firstRecord}</firstRecord>
     *                  <count>{count}</count>
     *              </retrieveParameters>
     *          </job:retrieve>
     *      </SOAP-ENV:Body>
     *  </SOAP-ENV:Envelope>
     *
     * @param SID
     * @return
     * @throws SOAPException
     */
    public SOAPMessage retrieve(String SID, String queryId, RetrieveParameters retrieveParameters) throws SOAPException {
        SOAPMessage soapMessage = newSOAPMessage();

        // SOAP Body
        SOAPBody soapBody = soapMessage.getSOAPPart().getEnvelope().getBody();
        SOAPElement retrieveElement = soapBody.addChildElement("retrieve", "job");

        // Headers
        MimeHeaders headers = soapMessage.getMimeHeaders();

        String cookie = String.format("SID=%s", SID);
        headers.addHeader("Cookie", cookie);

        // RetrieveElement
        retrieveElement.addChildElement("queryId").setValue(queryId);
        if (retrieveParameters != null) retrieveParameters.insertElementIn(retrieveElement);

        soapMessage.saveChanges();

        return soapMessage;
    }

    private SOAPMessage newSOAPMessage() throws SOAPException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        SOAPPart soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("job", NS_WOKSEARCH);
        return soapMessage;
    }
}
