package org.example.LICourse.TaxiTask;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class TaxiSignalsConsumer {
    public final String bootstrapServers = "localhost:9092";
    public final String groupID = "ten_application";
    public final String topic = "input";
    public final String readMethod = "earliest";

        public List<Taxi> getTaxiConsumerList(){
            List<Taxi> taxiList = new ArrayList<>();

        //create Consumer config
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupID);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, readMethod);
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true"); //true for At-most-once delivery
        properties.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "10"); //min for At-most-once delivery

        //create Consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        //subscribe Consumer to topics
        consumer.subscribe(Arrays.asList(topic));

        //poll new data
        for (int i=0; i<100; i++){
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(50));

            for(ConsumerRecord<String, String> record : records){
                String[] temp = record.value().split(", "); //parse incoming String with GPS coord's to double
                taxiList.add(new Taxi(record.key(), Double.parseDouble(temp[0]), Double.parseDouble(temp[1]))); //write it to List as Taxi object
            }
        }
        return taxiList;
    }
}