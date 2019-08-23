package com.siotman.batchwos.batch;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@EnableBatchProcessing
@SpringBootApplication
@EntityScan(basePackages = {"com.siotman.batchwos.batch.domain.jpa"})
public class BatchwosBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchwosBatchApplication.class, args);
    }

    static final String RABBITMQ_ADDRESS    = "127.0.0.1:5672";
    static final String RABBITMQ_USERNAME   = "sejong";         // 임시 아이디
    static final String RABBITMQ_PASSWORD   = "sejong1234";     // 임시 비번
    static final String CREATE_EXCHANGE     = "create";
    static final String UPDATE_EXCHANGE     = "update";
    static final String TARGET_URLS_QUEUE   = "targetURLs";

    @Bean Queue targetURLsQueue()   { return new Queue(TARGET_URLS_QUEUE, true); }

    @Bean TopicExchange createEx()  { return new TopicExchange(CREATE_EXCHANGE); }
    @Bean TopicExchange updateEx()  { return new TopicExchange(UPDATE_EXCHANGE); }

    @Bean
    Binding createBinding(Queue targetURLsQueue, TopicExchange createEx) {
        return BindingBuilder.bind(targetURLsQueue).to(createEx).with("target.create.#");
    }

    @Bean
    Binding updateBinding(Queue targetURLsQueue, TopicExchange updateEx) {
        return BindingBuilder.bind(targetURLsQueue).to(updateEx).with("target.update.#");
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(RABBITMQ_ADDRESS);
        connectionFactory.setUsername(RABBITMQ_USERNAME);
        connectionFactory.setPassword(RABBITMQ_PASSWORD);
        return connectionFactory;
    }
}
