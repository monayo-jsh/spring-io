package com.example.messagingwithredis;

import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Receiver {
    private final static Logger log = LoggerFactory.getLogger(Receiver.class);

    private final AtomicInteger counter = new AtomicInteger();

    public void receiveMessage(String message) {
        log.info("Received : " + message);
        counter.incrementAndGet();
    }

    public int getCount() {
        return counter.get();
    }
}
