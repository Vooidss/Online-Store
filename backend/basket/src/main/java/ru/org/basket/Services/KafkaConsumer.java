package ru.org.basket.Services;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "order", groupId = "basket_consumer")
    public void listener(ConsumerRecord<String,String> consumerRecord){
        log.info(consumerRecord.value() + consumerRecord.key());
    }
}
