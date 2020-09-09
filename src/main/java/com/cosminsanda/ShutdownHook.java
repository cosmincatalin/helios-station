package com.cosminsanda;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.Producer;

@Log4j2
class ShutdownHook<K, V> extends Thread {

    private final Producer<K, V> kafkaProducer;

    public ShutdownHook(Producer<K, V> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
        log.info("Set-up kill switch.");
    }

    public void run() {
        log.info("Kafka producer closed.");
        kafkaProducer.close();
    }

}

