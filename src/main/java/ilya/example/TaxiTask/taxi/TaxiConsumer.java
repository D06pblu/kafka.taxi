package ilya.example.TaxiTask.taxi;


import com.fasterxml.jackson.databind.deser.std.JsonNodeDeserializer;
import com.fasterxml.jackson.databind.ser.std.JsonValueSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import ilya.example.TaxiTask.log.Log;
import ilya.example.TaxiTask.log.LoggingProducer;
import org.example.LICourse.TaxiTask.services.JsonDeserializer;
import org.example.LICourse.TaxiTask.services.Taxi;
import org.example.LICourse.TaxiTask.services.TaxiJsonDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class TaxiConsumer implements Runnable {

    public static final String BOOTSTRAP_SERVERS = "localhost:9092";
    public static final String GROUP_ID = "second_application";
    public static final String TOPIC = "output";
    public static final String READ_METHOD = "earliest";

    private final KafkaConsumer<String, Taxi> consumer;
    private final LoggingProducer loggingProducer;


    public TaxiConsumer(LoggingProducer loggingProducer) {
        this.loggingProducer = loggingProducer;

        //create Consumer config
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, TaxiJsonDeserializer.class.getName());
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
            ConsumerRecords<String, Taxi> consumerRecords = consumer.poll(Duration.ofMillis(50));
            for (ConsumerRecord<String, Taxi> record : consumerRecords) {
                Taxi taxi = record.value();
                double distance = TaxiService.updateDistance(taxi);
                loggingProducer.send(new Log("Taxi distance passed " + distance));
            }
            consumer.commitSync(); //for At-Least-once delivery
        }
    }

    @Override
    public void run() {
        listen();
    }
}
