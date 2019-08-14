package com.siotman.batchwos.batch.component;

import com.siotman.batchwos.batch.domain.jpa.Paper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ParsingTrigger {
    /* SourceType,UID$,targetURL$,SID$,EXTRA*/
    private final String MSG_FORMAT = "%s$,%s$,%s$,%s$,%s";

    @Autowired private RabbitTemplate rabbitTemplate;

    public enum TYPE {
        ADD_PARSE_DETAIL, UPDATE_PARSE_DETAIL;

        private Map<String, String> props;

        TYPE() {
            this.props = new HashMap<>();

            switch (this) {
                case ADD_PARSE_DETAIL:
                    this.props.put("sourceType", "DETAIL_LINK");
                    this.props.put("exchange", "create");
                    this.props.put("routingKey", "target.create.record");

                case UPDATE_PARSE_DETAIL:
                    this.props.put("sourceType", "DETAIL_LINK");
                    this.props.put("exchange", "update");
                    this.props.put("routingKey", "target.update.record");

                default:
                    break;
            }
        }
    }

    public void startOne(TYPE type, Paper paper, String SID, String extra) {
        String msg = String.format(MSG_FORMAT,
                type.props.get("sourceType"),
                paper.getUid(),
                paper.getSourceUrls().getSourceURL(),
                SID,
                extra
        );

        rabbitTemplate.convertAndSend(
                type.props.get("exchange"),
                type.props.get("routingKey"),
                msg
        );
    }
}
