package com.siotman.batchwos.batch.util;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageConsumer {
    @Autowired
    RabbitTemplate rt;


}
