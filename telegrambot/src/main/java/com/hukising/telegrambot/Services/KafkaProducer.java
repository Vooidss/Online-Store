package com.hukising.telegrambot.Services;

import com.hukising.telegrambot.Models.Product;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTemplate<String, Product> kafkaTemplateForProduct;

    public void sendMessage(ProducerRecord<String, Object> message){
        kafkaTemplate.send(message);
    }
    public void sendProduct(ProducerRecord<String, Product> message){
        kafkaTemplateForProduct.send(message);
    }
}