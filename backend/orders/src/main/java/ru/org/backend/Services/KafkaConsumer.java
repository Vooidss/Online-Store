package ru.org.backend.Services;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "basket", groupId = "order_consumer")
    public void listener(ConsumerRecord<String,String> consumerRecord){
        log.info(consumerRecord.value() + consumerRecord.key());
    }

}
