package org.example.LICourse.TaxiTask;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.DoubleDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

public class TaxiCurrentDistanceConsumer {

    public final String bootstrapServers = "localhost:9092";
    public final String groupID = "second_application";
    public String topic = "output";
    public String readMethod = "earliest";

        public Map<String, Double> getTaxiCurrentDistances(){
            Map<String, Double> taxiCurrentDistance = new HashMap<>();

        //create Consumer config
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, DoubleDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupID);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, readMethod);
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false"); //false for At-Least-Once delivery

        //create Consumer
        KafkaConsumer<String, Double> consumer = new KafkaConsumer<>(properties);

        //subscribe Consumer to topics
        consumer.subscribe(Collections.singleton(topic));
        consumer.commitSync(); //for At-Least-once delivery

            ConsumerRecords<String, Double> records;
        //poll new data
        for (int i=0; i<100; i++){
            records = consumer.poll(Duration.ofMillis(50));

            for(ConsumerRecord<String, Double> record : records){
                taxiCurrentDistance.put(record.key(), record.value());
            }
        }
            System.out.println(taxiCurrentDistance);
        return taxiCurrentDistance;
    }
}