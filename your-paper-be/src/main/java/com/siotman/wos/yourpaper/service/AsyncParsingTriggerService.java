package com.siotman.wos.yourpaper.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siotman.wos.yourpaper.domain.dto.PaperDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AsyncParsingTriggerService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final URL parsingTriggerUrl = new URL("http://localhost:9402/parseAll");

    @Autowired
    private AsyncRunner asyncRunner;

    public AsyncParsingTriggerService() throws MalformedURLException {
    }

    public void triggerAll(List<PaperDto> list) {
        Map<String, PaperDto> requestBodyMap = new HashMap<>();
        for (PaperDto dto : list) {
            requestBodyMap.put(dto.getUid(), dto);
        }

        asyncRunner.run(() -> {
            try {
                _postRequest(parsingTriggerUrl, requestBodyMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


    private String _postRequest(URL url, Map requestBodyMap) throws IOException {
        String responseBodyString;

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        try (DataOutputStream writer = new DataOutputStream(conn.getOutputStream())) {
            String requestBody = objectMapper.writeValueAsString(requestBodyMap);

            writer.write(requestBody.getBytes());
            writer.flush();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                responseBodyString = reader.lines().collect(Collectors.joining("\n"));
            }
        } finally {
            conn.disconnect();
        }
        return responseBodyString;
    }
}
