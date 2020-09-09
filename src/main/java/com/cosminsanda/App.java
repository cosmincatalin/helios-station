package com.cosminsanda;

import com.audienceproject.util.cli.Arguments;
import com.typesafe.config.ConfigFactory;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

@Log4j2
public class App {

    public static void main(String[] args) throws InterruptedException {

        val conf = ConfigFactory.load();
        val arguments = new Arguments(args);

        val city = arguments.get("city");
        val notSorted = arguments.isSet("not-sorted");

        val props = Util.toProperties(conf.getConfig("kafka.producer"));

        val runtime = Runtime.getRuntime();

        val kafkaProducer = new KafkaProducer<>(props);
        runtime.addShutdownHook(new ShutdownHook<>(kafkaProducer));

        var recordBuilder = new RecordBuilder(city, notSorted);

        while (true) {
            log.info("Publishing...");
            kafkaProducer.send(new ProducerRecord<>(conf.getString("kafka.topic"), null, recordBuilder.getRecord()));
            Thread.sleep(4000);
        }
    }
}
