package org.example.LICourse.TaxiTask.producers;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.DoubleSerializer;
import org.apache.kafka.common.serialization.FloatSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Map;
import java.util.Properties;

public class TaxiCurrentDistanceProducer {
    public final String bootstrapServers = "localhost:9092";
    public final String topic = "output";
    public Double value;
    public String key;

    public void sendDataToTopic(Map<String, Double> distances) {
        // Create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, DoubleSerializer.class.getName());

        KafkaProducer<String, Double> producer = new KafkaProducer<>(properties); // Create Producer

        for (int i=0; i<distances.size(); i++) {
            key="id"+i;     //parse Map to separate values and send
            value= distances.get("id"+i);
            ProducerRecord<String, Double> record = new ProducerRecord<>(topic, key, value);
            producer.send(record);
        }
        producer.close();
    }
}



