package com.onlinestore.backend.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.onlinestore.backend.DTO.OrderPriceOfUserDTO;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaConsumer {

    private final KafkaProducer kafkaProducer;
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;
    private  OrderPriceOfUserDTO orderPriceOfUserDTO;

    @KafkaListener(topics = "basket", groupId = "authorization_group")
    public void setId(ConsumerRecord<String, String> consumerRecord){
        log.info(consumerRecord.key() + " :  " + consumerRecord.value());
        if(consumerRecord.key().equals("token")){

            String token = consumerRecord.value();
            String login = jwtService.extractLogin(token);
            int id;

            try {
                id = userService.getByLogin(login).getId();
            }catch (NotFoundException e){
                log.error("Пользователь не найден");
                log.error(e.getMessage());
                throw new NotFoundException("Пользователь не найден");
            }

            kafkaProducer.sendMessage(
                    new ProducerRecord<>(
                            "authorization",
                            "id",
                            id
                    )
            );
        }
    }

    @KafkaListener(topics = "order", groupId = "authorization_group")
    public void listener(ConsumerRecord<String, String> consumerRecord){
        log.info(consumerRecord.key() + " :  " + consumerRecord.value());
        if(consumerRecord.key().equals("userId")){

            int id = Integer.parseInt(consumerRecord.value());
            int money = userService.findMoneyById(id);

            kafkaProducer.sendMessage(
                       new ProducerRecord<>(
                               "authorization",
                               "money",
                               money
               )
            );
        }
    }

    @KafkaListener(topics = "price", groupId = "authorization_group")
    public void listener(String message) throws JsonProcessingException {
        log.info("Message: {}",message);

        orderPriceOfUserDTO = objectMapper.readValue(message, OrderPriceOfUserDTO.class);

        userService.writeOffMoney(orderPriceOfUserDTO.getUserId(), orderPriceOfUserDTO.getOrderPrice());
    }

}
