package cloud.ptl.kafka.serde;

import cloud.ptl.kafka.message.HarvestRequestMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

public class HarvestRequestMessageSerde implements Serializer<HarvestRequestMessage>, Deserializer<HarvestRequestMessage> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public HarvestRequestMessage deserialize(String s, byte[] bytes) {
        try {
            return objectMapper.readValue(s, HarvestRequestMessage.class);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error when deserializing byte[] to HarvestRequestMessage");
        }
    }

    @Override
    public HarvestRequestMessage deserialize(String topic, Headers headers, byte[] data) {
        try {
            return objectMapper.readValue(data, HarvestRequestMessage.class);
        } catch (IOException e) {
            throw new SerializationException("Error when deserializing byte[] to HarvestRequestMessage");
        }
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, HarvestRequestMessage harvestRequestMessage) {
        try {
            return objectMapper.writeValueAsBytes(harvestRequestMessage);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error when serializing HarvestRequestMessage to bytes");
        }
    }

    @Override
    public byte[] serialize(String topic, Headers headers, HarvestRequestMessage data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error when serializing HarvestRequestMessage to bytes");
        }
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
