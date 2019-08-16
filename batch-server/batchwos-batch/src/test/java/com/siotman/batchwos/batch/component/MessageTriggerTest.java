package com.siotman.batchwos.batch.component;

import com.rabbitmq.client.*;
import com.siotman.batchwos.batch.domain.jpa.Paper;
import com.siotman.batchwos.batch.domain.jpa.SourceUrls;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageTriggerTest {
    @Autowired ParsingTrigger parsingTrigger;
    @Autowired ConnectionFactory connectionFactory;

    @Test
    public void triggerTest() throws IOException {
        // 메시징 확인용 임시 컨슈머
        Channel channel = connectionFactory.createConnection().createChannel(true);
        channel.basicConsume("targetURLs", new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(
                    String consumerTag,
                    Envelope envelope,
                    AMQP.BasicProperties properties,
                    byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                JSONParser parser = new JSONParser(message);
                try {
                    JSONObject jsonObj = (JSONObject) parser.parse();

                    assert jsonObj.get("UID").equals("TEST_UID");
                    assert jsonObj.get("sourceType").equals("DETAIL_LINK");
                    assert jsonObj.get("targetURL").equals("TEST_URL");
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println(" [x] Received '" + message + "'");
            }
        });

        parsingTrigger.startOne(
            ParsingTrigger.TYPE.ADD_PARSE_DETAIL,
            Paper.builder()
                    .uid("TEST_UID")
                    .sourceUrls(SourceUrls.builder()
                            .sourceURL("TEST_URL").build())
                    .build(),
        "TEST01");
    }
}
