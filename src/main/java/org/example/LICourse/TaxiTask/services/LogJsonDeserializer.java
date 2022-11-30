package org.example.LICourse.TaxiTask.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import ilya.example.TaxiTask.log.Log;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class LogJsonDeserializer implements Deserializer<Log> {
    private final ObjectMapper objectMapper;

    public LogJsonDeserializer() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Log deserialize(String topic, byte[] data) {
        try {
            return this.objectMapper.readValue(data, Log.class);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }
}
