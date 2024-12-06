package com.onlinestore.backend.Deserialize;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinestore.backend.DTO.ProductResponse;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.List;

public class ProductDeserializer implements Deserializer<List<ProductResponse>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<ProductResponse> deserialize(String topic, byte[] bytes) {
        try {
            return List.of(objectMapper.readValue(bytes, ProductResponse[].class));
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing product", e);
        }
    }
}
