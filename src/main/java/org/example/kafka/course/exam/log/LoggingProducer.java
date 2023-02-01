package org.example.kafka.course.exam.log;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.kafka.course.exam.services.JsonSerializer;

import java.io.Closeable;
import java.io.IOException;
import java.util.Properties;

public class LoggingProducer implements Closeable {

    public static final String BOOTSTRAP_SERVERS = "localhost:9092";
    public static final String TOPIC = "output";

    private final KafkaProducer<String, Log> producer;

    public LoggingProducer() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        this.producer = new KafkaProducer<>(properties); // create producer
    }

    public void send(Log log) {
        // send log to log topic
        producer.send(new ProducerRecord<>(TOPIC, log.getMessage(), log));
    }

    @Override
    public void close() throws IOException {
        producer.close();
    }
}
