package com.hukising.telegrambot.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hukising.telegrambot.Models.Product;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.List;

public class ProductsDeserializer implements Deserializer<List<Product>>{

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Product> deserialize(String topic, byte[] bytes) {
        try {
            return List.of(objectMapper.readValue(bytes, Product[].class));
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing product", e);
        }
    }
}
