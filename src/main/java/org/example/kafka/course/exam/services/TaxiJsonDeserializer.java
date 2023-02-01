package org.example.kafka.course.exam.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.example.kafka.course.exam.taxi.Taxi;

import java.io.IOException;

public class TaxiJsonDeserializer implements Deserializer<Taxi> {
    private final ObjectMapper objectMapper;


    public TaxiJsonDeserializer() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Taxi deserialize(String topic, byte[] data) {
        try {
            return this.objectMapper.readValue(data, Taxi.class);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }
}
