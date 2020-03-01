package com.siotman.wos.yourpaper.domain.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siotman.wos.yourpaper.domain.json.JournalImpactJson;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import java.io.IOException;

@Convert
public class JsonJournalImpactConverter implements AttributeConverter<JournalImpactJson, String> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(JournalImpactJson journalImpactJson) {
        if (journalImpactJson == null) return null;

        String json = null;
        try {
            json = objectMapper.writeValueAsString(journalImpactJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }

    @Override
    public JournalImpactJson convertToEntityAttribute(String json) {
        if (json == null) return null;

        JournalImpactJson journalImpactJson = null;
        try {
            journalImpactJson = objectMapper.readValue(json, JournalImpactJson.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return journalImpactJson;
    }
}
