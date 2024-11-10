package com.onlinestore.backend.Services;

import com.onlinestore.backend.Models.Products;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaConsumer {

    private final ProductService productService;
    private final KafkaProducer kafkaProducer;

    @Transactional
    @KafkaListener(topics = "telegram", groupId = "product_consumer")
    public void getProduct(ConsumerRecord<String, String> consumerRecord){
        if(consumerRecord.key().equals("getProduct")){
            List<Products> products = productService.findAllList();

            kafkaProducer.sendProduct(
                    new ProducerRecord<>(
                            "product",
                            "products",
                            products
                    )
            );

        }
    }

}
