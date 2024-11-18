package com.hukising.telegrambot.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hukising.telegrambot.Models.Product;
import org.apache.kafka.common.serialization.Serializer;

public class ProductSerializer implements Serializer<Product> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProductSerializer() {
    }

    @Override
    public byte[] serialize(String s, Product product) {
        try {
            return objectMapper.writeValueAsBytes(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
