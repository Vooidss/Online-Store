package ru.org.backend.Services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaConsumer {

    private final KafkaProducer kafkaProducer;
    private final UserService userService;

    @KafkaListener(topics = "order", groupId = "authorization_group")
    public void listener(ConsumerRecord<String, String> consumerRecord){
        log.info(consumerRecord.key() + " :  " + consumerRecord.value());
        if(consumerRecord.key().equals("message") && consumerRecord.value().equals("money")){

            
            int money = userService.findMoneyCurrentUser();

            kafkaProducer.sendMessage(
                       new ProducerRecord<>(
                               "authorization",
                               "money",
                               money
               )
            );
        }
    }

}
