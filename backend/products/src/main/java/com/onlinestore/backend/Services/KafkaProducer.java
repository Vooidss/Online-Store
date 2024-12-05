package com.onlinestore.backend.Services;

import com.onlinestore.backend.DTO.ProductDTO;
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

    private final KafkaTemplate<String, List<Products>> kafkaTemplateProduct;
    private final KafkaTemplate<String, List<ProductDTO>> kafkaTemplateProductDTO;

    public void sendProduct(ProducerRecord<String, List<Products>> message){
        kafkaTemplateProduct.send(message);
    }
    public void sendProductDTO(ProducerRecord<String, List<ProductDTO>> message){
        kafkaTemplateProductDTO.send(message);
    }
}