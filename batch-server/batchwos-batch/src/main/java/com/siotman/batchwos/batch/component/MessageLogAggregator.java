package com.siotman.batchwos.batch.component;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageLogAggregator {

    @RabbitListener(queues = "flow")
    public void consumeFlow(String msg) throws JSONException {
        JSONObject jsonObj;
        try {
            jsonObj = new JSONObject(msg);
        } catch (JSONException e) {
            StringBuilder sb = new StringBuilder();
            for (String val : msg.split(",")) {
                sb.append((char) Integer.parseInt(val));
            }

            jsonObj = new JSONObject(sb.toString());
        }

        System.out.println("FLOOOOW: " + jsonObj.toString());
    }
}
