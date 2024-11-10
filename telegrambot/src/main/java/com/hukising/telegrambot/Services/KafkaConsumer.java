package com.hukising.telegrambot.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hukising.telegrambot.DTO.ProductDTO;
import com.hukising.telegrambot.Models.Product;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaConsumer {

    private final ProductDTO productDTO;

    @KafkaListener(topics = "product", groupId = "telegram_consumer")
    public void getProduct(ConsumerRecord<String, List<Product>> consumerRecord) throws JsonProcessingException {
        if(consumerRecord.key().equals("products")){
            List<Product> response = consumerRecord.value();

            productDTO.setProducts(response);

        }
    }

}
