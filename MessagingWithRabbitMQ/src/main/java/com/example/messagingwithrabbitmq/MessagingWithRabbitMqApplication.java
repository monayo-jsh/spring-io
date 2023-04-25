package com.example.messagingwithrabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessagingWithRabbitMqApplication {

    private final static Logger log = LoggerFactory.getLogger(MessagingWithRabbitMqApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MessagingWithRabbitMqApplication.class, args);
    }
}
