package com.siotman.wos.jaxws2rest.component;

import com.siotman.wos.jaxws2rest.JaxwsApplication;
import com.siotman.wos.jaxws2rest.domain.dto.LamrResultsDto;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LamrServiceWrapper {
    private final URL ENDPOINT_URL;
    private final String XML_USER_INFO;

    private final String WOS_REQUEST_XML;
    private final String DRCI_REQUEST_XML;
    private final String JCR_REQUEST_XML;

    public List<LamrResultsDto> requestCoreCollectionData(List<String> uids) throws IOException {
        StringBuilder result = new StringBuilder();

        String lookupData = _buildLookupData(uids);
        String requestXml = WOS_REQUEST_XML.replace("${lookupData}", lookupData);

        InputStream responseInputStream = _request(requestXml);

        BufferedReader br = new BufferedReader(new InputStreamReader(responseInputStream));
        String line;
        while ((line = br.readLine()) != null) {
            result.append(line);
        }
        List<LamrResultsDto> lamrResultsDtos = _parseWosResponseBody(uids.size(), result.toString());
        return lamrResultsDtos;
    }

    public void requestDataCitationIndexData(List<String> uids) {
        String requestXml;

    }

    public void requestJcrData(List<String> uids) {
        String requestXml;

    }

    public LamrServiceWrapper() throws IOException {
        ENDPOINT_URL = new URL("https://ws.isiknowledge.com/cps/xrpc");

        XML_USER_INFO = new StringBuilder()
                .append("<map>\n")
                    .append("<val name=\"username\">").append(JaxwsApplication.WS_WOS_USERNAME).append("</val>")
                    .append("<val name=\"password\">").append(JaxwsApplication.WS_WOS_PASSWORD).append("</val>")
                .append("</map>\n")
                .toString();


        WOS_REQUEST_XML     = _loadXml("lamr-wos-template.xml");
        DRCI_REQUEST_XML    = _loadXml("lamr-jcr-template.xml");
        JCR_REQUEST_XML     = _loadXml("lamr-drci-template.xml");
    }

    private List<LamrResultsDto> _parseWosResponseBody(int size, String responseBody) {
        List<LamrResultsDto> resultsDtos = new ArrayList<>();
        while(resultsDtos.size() < size) resultsDtos.add(null);

        InputSource is = new InputSource((new StringReader(responseBody)));

        DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();
        XPathFactory xPathFactory       = XPathFactory.newInstance();

        DocumentBuilder builder = null;
        try {
            builder             = factory.newDocumentBuilder();

            Document document       = builder.parse(is);
            XPath xPath             = xPathFactory.newXPath();
            XPathExpression compile = xPath.compile("/response/fn/map/map");

            NodeList itemList = (NodeList) compile.evaluate(document, XPathConstants.NODESET);
            for (int i = 0; i < itemList.getLength(); i++) {
                LamrResultsDto dto = new LamrResultsDto();

                Node item = itemList.item(i).getFirstChild();
                NodeList childNodes = item.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node prop = childNodes.item(j);

                    String name     = prop.getAttributes().getNamedItem("name").getNodeValue();
                    String value    = prop.getFirstChild().getNodeValue();
                    if (value == null) {
                        System.out.println("name : " + name + ", value : null");
                        System.out.println(prop.getTextContent());
                    }
                    dto.setByName(name, value);
                }
                int idx = Integer.parseInt(
                        itemList.item(i).getAttributes()
                        .getNamedItem("name").getNodeValue());
                resultsDtos.set(idx, dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultsDtos;
    }

    private InputStream _request(String xmlBody) throws IOException {
        HttpURLConnection con = (HttpURLConnection) ENDPOINT_URL.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setRequestProperty("accept-charset", "UTF-8");
        con.setRequestProperty("content-type", "application/x-www-form-urlencoded");

        OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
        writer.write(xmlBody);
        writer.flush();
        return con.getInputStream();
    }

    private String _loadXml(String path) throws IOException {
        String result;

        InputStream resource = new ClassPathResource(path).getInputStream();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(resource))) {
            result = br.lines().collect(Collectors.joining());
        }

        return result.replace("${userInfo}", XML_USER_INFO);
    }

    private String _buildLookupData(List<String> uids) {
        StringBuilder lookupData = new StringBuilder();

        for (int i = 0; i < uids.size(); i++) {
            lookupData
                .append("<map name=\"").append(i).append("\">\n")
                    .append("<val name=\"uid\">").append(uids.get(i)).append("</val>\n")
                .append("</map>\n");
        }

        return lookupData.toString();
    }
}
