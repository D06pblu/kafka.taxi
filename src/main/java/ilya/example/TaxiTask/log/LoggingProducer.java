package ilya.example.TaxiTask.log;


import com.fasterxml.jackson.databind.ser.std.JsonValueSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import ilya.example.TaxiTask.log.Log;
import org.example.LICourse.TaxiTask.services.JsonSerializer;

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

        this.producer = new KafkaProducer<>(properties); // Create Producer
    }

    public void send(Log log) {
        // Сделать всё что нужно и отправить лог в топик логов
        producer.send(new ProducerRecord<>(TOPIC, log.getMessage(), log));
    }

    @Override
    public void close() throws IOException {
        producer.close();
    }
}
