package com.siotman.wos.yourpaper.domain.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siotman.wos.yourpaper.domain.json.ParsedAuthorJson;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.List;

public class JsonParsedAuthorListConverter implements AttributeConverter<List<ParsedAuthorJson>, String> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<ParsedAuthorJson> parsedAuthorJsons) {
        if (parsedAuthorJsons == null) return "[]";

        String json = null;

        try {
            json = objectMapper.writeValueAsString(parsedAuthorJsons);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }

    @Override
    public List<ParsedAuthorJson> convertToEntityAttribute(String json) {
        List<ParsedAuthorJson> parsedAuthorJsons = null;
        try {
            parsedAuthorJsons = objectMapper.readValue(json, new TypeReference<List<ParsedAuthorJson>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return parsedAuthorJsons;
    }
}
