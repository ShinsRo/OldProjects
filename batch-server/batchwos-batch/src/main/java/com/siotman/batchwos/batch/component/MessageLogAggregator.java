package com.siotman.batchwos.batch.component;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageLogAggregator {

    @RabbitListener(queues = "flow")
    public void consumeFlow(String msg) {
        System.out.println("FLOOOOW: " + msg);
    }
}
