package org.example.LICourse.TaxiTask;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TaxiSignalsProducer {
    public static int n=5; //сколько такси мы хотим создать
    public static int m=20; //сколько движений каждого такси хотим создать

    public static void main(String[] args) {
        String bootstrapServers = "localhost:9092";
        Logger logger = LoggerFactory.getLogger(TaxiSignalsProducer.class);
        String topic = "input";
        String value;
        String key;
        Taxi taxi;

//generating N taxi with random start coordinates
        List<Taxi> taxis = new ArrayList<>();
        for(int i=0; i<TaxiSignalsProducer.n; i++){
            taxi = new Taxi("id"+i);
            taxis.add(taxi);
        }

// Create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Create Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        //generate M moves of N taxi and create producer record
        for (int m=0; m<TaxiSignalsProducer.m; m++) {
            for (int n=0; n<taxis.size(); n++) {
                double tempLatitude;
                double tempLongitude;
                String tempID;

                tempLatitude = taxis.get(n).getLatitude() + (((Math.random() * 6)) / 1000); //тут только плюсы, надо подумать как сделать ранодомное движение
                tempLongitude = taxis.get(n).getLongitude() + (((Math.random() * 6)) / 1000);
                tempID = taxis.get(n).getId();
                taxis.set(n, new Taxi(tempID, tempLatitude, tempLongitude)); //set renew taxi object back to list
                key = tempID;
                value = tempLatitude + ", " + tempLongitude;
                ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);

                // Send data (отсылка асинхронная. Можно сделать синхронную, но крайне(!) не рекомендуется для продакшна
                producer.send(record, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        if (exception == null) {
//                            logger.info("Recieved new Metadata. \n" +
//                                    "Topic: " + metadata.topic() + "\n" +
//                                    "Partition: " + metadata.partition() + "\n" +
//                                    "Offset: " + metadata.offset() + "\n" +
//                                    "Timestamp: " + metadata.timestamp());
                        } else { //если ошибка, то делаем это
                            logger.error("Error while producing: ", exception);
                        }
                    }
                });
                producer.flush();
            }
        }
        producer.close();
    }
}
