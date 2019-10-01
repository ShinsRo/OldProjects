package com.siotman.wos.parsingtrigger;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan(basePackages = {"com.siotman.wos.parsingtrigger.domain.entity"})
public class ParsingTriggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParsingTriggerApplication.class, args);
    }

    static final String RABBITMQ_ADDRESS    = "127.0.0.1:5672";
    static final String RABBITMQ_USERNAME   = "local";          // 임시 아이디
    static final String RABBITMQ_PASSWORD   = "local";          // 임시 비번
    static final String TARGET_EX           = "target";
    static final String LOG_EX              = "logs";
    static final String TARGET_URLS_QUEUE   = "targetURLs";
    static final String LOG_INFO_QUEUE      = "logInfo";

    @Bean
    Queue targetURLsQueue()   { return new Queue(TARGET_URLS_QUEUE, true); }

    @Bean
    Queue logInfoQueue()   { return new Queue(LOG_INFO_QUEUE, true); }

    @Bean
    TopicExchange targetEx()  { return new TopicExchange(TARGET_EX); }
    @Bean
    TopicExchange logEx()  { return new TopicExchange(LOG_EX); }

    @Bean
    Binding targetExBinding(Queue targetURLsQueue, TopicExchange targetEx) {
        return BindingBuilder.bind(targetURLsQueue).to(targetEx).with("target.update.#");
    }

    @Bean
    Binding logsExBinding(Queue targetURLsQueue, TopicExchange logEx) {
        return BindingBuilder.bind(targetURLsQueue).to(logEx).with("logs.info.#");
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
