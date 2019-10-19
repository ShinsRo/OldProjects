package com.siotman.wos.yourpaper.domain.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Converter
public class JsonListConverter implements AttributeConverter<List<String>, String> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> list) {
        if (list == null || list.size() == 0) return "[]";

        String jsonArray = null;
        try {
            jsonArray = objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    @Override
    public List convertToEntityAttribute(String jsonArray) {
        if (jsonArray == null) return new ArrayList();

        List<String> list = null;
        try {
            list = objectMapper.readValue(jsonArray, List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
