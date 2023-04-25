package com.example.messagingwithrabbitmq;

import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Receiver {

    private final static Logger log = LoggerFactory.getLogger(Receiver.class);

    private final CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        log.info("Received : {}", message);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
