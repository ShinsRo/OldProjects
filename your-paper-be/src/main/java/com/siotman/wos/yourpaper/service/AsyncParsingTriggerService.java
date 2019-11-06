package com.siotman.wos.yourpaper.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siotman.wos.yourpaper.config.RabbitmqConfig;
import com.siotman.wos.yourpaper.domain.dto.PaperDto;
import com.siotman.wos.yourpaper.domain.dto.PaperUrlsDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AsyncParsingTriggerService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, String> MSG_FORMAT = new HashMap<String, String>() {{
        put("sourceType",   "");
        put("UID",          "");
        put("targetUrl",    "");
        put("extra",        "");
    }};

    @Autowired
    private AsyncRunner asyncRunner;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public AsyncParsingTriggerService() {
    }


    public void triggerAll(List<PaperDto> list) {

        asyncRunner.run(() -> {
            try {
                for (PaperDto dto : list) {
                    PaperUrlsDto urlsDto = dto.getPaperUrlsDto();
                    startOne(TYPE.DETAIL, dto.getUid(), urlsDto.getSourceUrl(), null);

                    if (dto.getTimesCited() != null && dto.getTimesCited() > 0)
                        startOne(TYPE.TC_DATA, dto.getUid(), urlsDto.getCitingArticlesUrl(), null);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

    public void startOne(TYPE type, String uid, String url, String extra) throws JsonProcessingException {
        MSG_FORMAT.put("sourceType",    type.format.get("sourceType"));
        MSG_FORMAT.put("UID",           uid);
        MSG_FORMAT.put("targetUrl",     url);
        MSG_FORMAT.put("extra",         extra);

        String json     = objectMapper.writeValueAsString(MSG_FORMAT);
//        Message message = MessageBuilder
//                .withBody(json.getBytes())
//                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
//                .build();
        rabbitTemplate.convertAndSend(
                type.format.get("exchange"),
                type.format.get("routingKey"),
                json
        );
    }

    public enum TYPE {
        DETAIL(new HashMap<String, String>() {{
            put("sourceType", "DETAIL_LINK");
            put("exchange", RabbitmqConfig.PARSING_TARGET_EX);
            put("routingKey", RabbitmqConfig.PARSING_TARGET_ROUTING_KEY_PREFIX + "new");
        }}),

        TC_DATA(new HashMap<String, String>() {{
            put("sourceType", "CITING_LINK");
            put("exchange", RabbitmqConfig.PARSING_TARGET_EX);
            put("routingKey",  RabbitmqConfig.PARSING_TARGET_ROUTING_KEY_PREFIX + "update");
        }});

        private Map<String, String> format;

        TYPE(Map<String, String> format) {
            this.format = format;
        }
    }
}
