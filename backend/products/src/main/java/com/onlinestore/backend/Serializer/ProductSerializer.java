package com.onlinestore.backend.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinestore.backend.Models.Products;
import org.apache.kafka.common.serialization.Serializer;

import java.util.List;

public class ProductSerializer implements Serializer<List<Products>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProductSerializer() {
    }

    @Override
    public byte[] serialize(String topic, List<Products> products) {
        try {
            return objectMapper.writeValueAsBytes(products);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
