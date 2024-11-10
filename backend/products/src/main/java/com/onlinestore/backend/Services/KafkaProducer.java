package com.onlinestore.backend.Services;

import com.onlinestore.backend.Models.Products;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, List<Products>> kafkaTemplate;

    public void sendProduct(ProducerRecord<String, List<Products>> message){
        kafkaTemplate.send(message);
    }
}