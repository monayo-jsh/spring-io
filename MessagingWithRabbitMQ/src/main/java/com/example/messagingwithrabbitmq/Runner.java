package com.example.messagingwithrabbitmq;

import com.example.messagingwithrabbitmq.config.RabbitMqConfiguration;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private final static Logger log = LoggerFactory.getLogger(Runner.class);

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public Runner(RabbitTemplate rabbitTemplate, Receiver receiver) {
        this.rabbitTemplate = rabbitTemplate;
        this.receiver = receiver;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Sending message ...");

        rabbitTemplate.convertAndSend(RabbitMqConfiguration.TOPIC_EXCHANGE_NAME, "foo.bar.baz", "Hello from RabbitMQ!");

        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}
