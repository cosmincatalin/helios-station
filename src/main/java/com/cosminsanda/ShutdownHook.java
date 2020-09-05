package com.cosminsanda;

import org.apache.kafka.clients.producer.Producer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class ShutdownHook<K, V> extends Thread {

    private final Producer<K, V> kafkaProducer;
    private final Logger logger = LogManager.getLogger(ShutdownHook.class);

    public ShutdownHook(Producer<K, V> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
        logger.info("Set-up kill switch.");
    }

    public void run() {
        logger.info("Kafka producer closed.");
        kafkaProducer.close();
    }

}

