package com.siotman.wos.yourpaper.domain.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siotman.wos.yourpaper.domain.json.CitingPaperJson;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import java.io.IOException;
import java.util.List;

@Convert
public class JsonCitingPaperListConverter implements AttributeConverter<List<CitingPaperJson>, String> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<CitingPaperJson> list) {
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
    public List<CitingPaperJson> convertToEntityAttribute(String json) {
        if (json == null) return null;

        List<CitingPaperJson> citingPaperJson =  null;

        try {
            citingPaperJson = objectMapper.readValue(json, new TypeReference<List<CitingPaperJson>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return citingPaperJson;
    }
}
