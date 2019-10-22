package com.siotman.wos.yourpaper.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setAddresses(RABBITMQ_ADDRESS);
//        connectionFactory.setUsername(RABBITMQ_USERNAME);
//        connectionFactory.setPassword(RABBITMQ_PASSWORD);
//        return connectionFactory;
//    }
    public static final String PARSING_TARGET_EX   = "url-exchange";
    public static final String LOG_EX              = "log-exchange";

    public static final String TARGET_URL_QUEUE    = "targetUrl-queue";
    public static final String LOG_QUEUE           = "log-queue";

    public static final String PARSING_TARGET_ROUTING_KEY_PREFIX    = "parse.target.";
    public static final String LOG_ROUTING_KEY_PREFIX               = "parse.target.";

    @Bean
    Queue targetUrlQueue()   { return new Queue(TARGET_URL_QUEUE, true); }
    @Bean
    Queue logQueue()   { return new Queue(LOG_QUEUE, true); }

    @Bean
    TopicExchange urlExchange()  { return new TopicExchange(PARSING_TARGET_EX); }
    @Bean
    TopicExchange logExchange()  { return new TopicExchange(LOG_EX); }

    @Bean
    Binding targetExBinding(Queue targetUrlQueue, TopicExchange urlExchange) {
        return BindingBuilder.bind(targetUrlQueue).to(urlExchange).with(PARSING_TARGET_ROUTING_KEY_PREFIX + "#");
    }
    @Bean
    Binding logsExBinding(Queue logQueue, TopicExchange logExchange) {
        return BindingBuilder.bind(logQueue).to(logExchange).with(LOG_ROUTING_KEY_PREFIX + "#");
    }

//    @Bean
//    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
//        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
//        return rabbitTemplate;
//    }
//
//    @Bean
//    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
}
