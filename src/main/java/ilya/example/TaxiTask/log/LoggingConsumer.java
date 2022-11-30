package ilya.example.TaxiTask.log;


import com.fasterxml.jackson.databind.deser.std.JsonNodeDeserializer;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.fasterxml.jackson.databind.ser.std.JsonValueSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.LICourse.TaxiTask.services.JsonDeserializer;
import org.example.LICourse.TaxiTask.services.LogJsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class LoggingConsumer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingConsumer.class);
    public static final String BOOTSTRAP_SERVERS = "localhost:9092";
    public static final String GROUP_ID = "second_application";
    public static final String TOPIC = "output";
    public static final String READ_METHOD = "earliest";

    private final KafkaConsumer<String, Log> consumer;
    private final LoggingProducer loggingProducer;

    public LoggingConsumer(LoggingProducer loggingProducer) {
        this.loggingProducer = loggingProducer;

        //create Consumer config
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LogJsonDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, READ_METHOD);
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        //create Consumer
        this.consumer = new KafkaConsumer<>(properties);

        //subscribe Consumer to topics
        consumer.subscribe(Collections.singleton(TOPIC));
    }

    public void listen() {
        while (true) {
            ConsumerRecords<String, Log> consumerRecords = consumer.poll(Duration.ofMillis(50));
            for (ConsumerRecord<String, Log> record : consumerRecords) {
                consumer.commitSync(); //for At-most-once delivery
                Log polledLog = new Log();
                LOGGER.info("Logging consumer: log message = '{}'", polledLog);
            }
        }
    }
    @Override
    public void run () {
        listen();
    }
}
