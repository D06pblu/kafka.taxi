package org.example.kafka.course.exam.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import java.io.IOException;

public class JsonDeserializer<T> implements Deserializer<T> {
    private final ObjectMapper objectMapper;

    private final Class<T> toClazz;

    public JsonDeserializer(Class<T> toClazz) {
        this(new ObjectMapper(), toClazz);
    }

    public JsonDeserializer(ObjectMapper objectMapper, Class<T> toClazz) {
        this.objectMapper = objectMapper;
        this.toClazz = toClazz;
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            return this.objectMapper.readValue(data, toClazz);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }
}
