package com.siotman.wos.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siotman.wos.domain.jpa.Paper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ParsingTrigger {
    /* SourceType,UID$,targetURL$,SID$,EXTRA*/

    private final Map<String, String> MSG_FORMAT
            = new HashMap<String, String>() {{
                put("sourceType",   "");
                put("UID",          "");
                put("targetURL",    "");
                put("extra",        "");
    }};


    @Autowired private RabbitTemplate rabbitTemplate;
    public enum TYPE {
        ADD_PARSE_DETAIL    (new HashMap<String, String>() {{
            put("sourceType", "DETAIL_LINK");
            put("exchange", "create");
            put("routingKey", "target.create.record");
        }}),

        UPDATE_PARSE_DETAIL (new HashMap<String, String>() {{
            put("sourceType", "DETAIL_LINK");
            put("exchange", "update");
            put("routingKey", "target.update.record");
        }});

        private Map<String, String> format;

        TYPE(Map<String, String> format) {
            this.format = format;
        }
    }

    public void startOne(TYPE type, Paper paper, String extra) {
        MSG_FORMAT.put("sourceType",    type.format.get("sourceType"));
        MSG_FORMAT.put("UID",           paper.getUid());
        MSG_FORMAT.put("targetURL",     paper.getSourceUrls().getSourceURL());
        MSG_FORMAT.put("extra",         extra);

        ObjectMapper om = new ObjectMapper();
        String json = null;
        try {
            json = om.writeValueAsString(MSG_FORMAT);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        rabbitTemplate.convertAndSend(
                type.format.get("exchange"),
                type.format.get("routingKey"),
                json
        );
    }
}
