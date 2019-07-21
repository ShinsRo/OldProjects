package com.siotman.batchwos.wsclient.lamr;

import com.siotman.batchwos.wsclient.lamr.domain.LamrRequestParameters;
import com.siotman.batchwos.wsclient.lamr.domain.TARGET_DB_TYPE;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class LAMRClient {
    /**
     * LAMR Service(Links Article Match Retrieval Service) 제약 사항
     *
     * [http://help.incites.clarivate.com/LAMRService/WebServiceOperationsGroup/requestResponseAPI.html]
     * - For Web of Science Core Collection or Data Citation Index, a single request may specify up to 50 different articles.
     * - For Journal Citation Reports, a single request may specify up to 50 different journals. One request may also specify different publication years of the same journal.
     * - You must submit a separate request for data from each databases: Web of Science Core Collection, Data Citation Index or Journal Citation Reports.
     * - If you want the same data elements for multiple articles, you may submit a single request. For example, if you want both DOIs and Times Cited counts for 25 articles, only one request is needed.
     * - You must make separate requests if you want different data for different articles. For example, if you want to obtain DOIs for some articles and Times Cited counts for other articles, you must submit two separate requests.
     */
    private String ENDPOINT_URL = "https://ws.isiknowledge.com/cps/xrpc";
    private URL url;

    private LAMRMessageGen lamrMessageGen = LAMRMessageGen.getInstance();

    public LAMRClient() throws IOException {
        url = new URL(ENDPOINT_URL);
    }

    public String request(TARGET_DB_TYPE targetDbType, List<LamrRequestParameters> params) {

        String requestXml = lamrMessageGen.getXmlData(targetDbType, params);
        StringBuilder content = new StringBuilder();

        OutputStreamWriter writer;
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("accept-charset", "UTF-8");
            con.setRequestProperty("content-type", "application/x-www-form-urlencoded");

            writer = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            writer.write(requestXml);
            writer.flush();
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }


}
