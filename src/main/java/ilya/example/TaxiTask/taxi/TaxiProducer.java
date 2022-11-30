package ilya.example.TaxiTask.taxi;

import com.fasterxml.jackson.databind.ser.std.JsonValueSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.LICourse.TaxiTask.services.JsonSerializer;
import org.example.LICourse.TaxiTask.services.Taxi;

import java.io.Closeable;
import java.util.Properties;

public class TaxiProducer implements Closeable {

    public static final String BOOTSTRAP_SERVERS = "localhost:9092";
    public static final String TOPIC = "input";

    private final KafkaProducer<String, Taxi> producer;

    public TaxiProducer() {
        // Create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        this.producer = new KafkaProducer<String, Taxi>(properties); // Create Producer
    }

    public void sendDataToTopic(Taxi taxi) {
        producer.send(new ProducerRecord<>(TOPIC, taxi.getId(), taxi));
    }

    @Override
    public void close() {
        producer.close();
    }
}



