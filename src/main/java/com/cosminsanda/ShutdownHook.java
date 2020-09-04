package com.cosminsanda;

import org.apache.kafka.clients.producer.Producer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class ShutdownHook<K, V> extends Thread {

    private final Producer<K, V> _kafkaProducer;
    private final Logger _logger = LogManager.getLogger(ShutdownHook.class);

    public ShutdownHook(Producer<K, V> kafkaProducer) {
        _kafkaProducer = kafkaProducer;
        _logger.info("Set-up kill switch.");
    }

    public void run() {
        _logger.info("Kafka producer closed.");
        _kafkaProducer.close();
    }

}

