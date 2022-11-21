package org.example.LICourse.TaxiTask;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Map;
import java.util.Properties;

public class TaxiLastDistanceProducer {
    String bootstrapServers = "localhost:9092";
    String topic = "output";
    String value;
    String key;

    public void sendDataToTopic(Map<String, Double> distances) {
        // Create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Create Producer
        for (int i=0; i<distances.size(); i++) {
            KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

            key="id"+i;     //parse Map to separate values and send
            value= String.valueOf(distances.get("id"+i));
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);

            producer.send(record);
            producer.close();
        }
    }
}



