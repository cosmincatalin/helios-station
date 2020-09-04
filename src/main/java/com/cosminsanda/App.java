package com.cosminsanda;

import com.audienceproject.util.cli.Arguments;
import com.typesafe.config.ConfigFactory;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;

public class App {

    public static void main(String[] args) throws InterruptedException {

        var conf = ConfigFactory.load();
        var logger = LogManager.getLogger(App.class);
        var arguments = new Arguments(args);

        var city = arguments.get("city");
        var sorted = arguments.isSet("sorted");

        var props = Util.toProperties(conf.getConfig("kafka.producer"));

        var runtime = Runtime.getRuntime();

        var kafkaProducer = new KafkaProducer<>(props);
        runtime.addShutdownHook(new ShutdownHook<>(kafkaProducer));

        var recordBuilder = new RecordBuilder(city, sorted);

        while (true) {
            logger.info("Publishing...");
            kafkaProducer.send(new ProducerRecord<>(conf.getString("kafka.topic"), null, recordBuilder.getRecord()));
            Thread.sleep(4000);
        }
    }
}
