package com.siotman.wos.yourpaper.domain.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Convert
public class JsonMapConverter implements AttributeConverter<Map<String, String>, String> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, String> map) {
        if (map == null) return null;

        String json = null;
        try {
            json = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String jsonMap) {
        if (jsonMap == null) return new HashMap<>();

        Map<String, String> map = null;
        try {
            map = objectMapper.readValue(jsonMap, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }
}
