package ru.org.backend.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import ru.org.backend.DTO.PriceOrderOfUserDTO;

@Service
@AllArgsConstructor
public class KafkaService {

    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;

    public void writeOffMoney(Integer userId, Integer orderPrice) throws JsonProcessingException {

        String value = objectMapper.writeValueAsString(new PriceOrderOfUserDTO(userId,orderPrice));

        kafkaProducer.sendMessage(
                new ProducerRecord<>(
                        "price",
                        value
                )
        );

    }
    public void getUserMoney(String id){

        kafkaProducer.sendMessage(
                new ProducerRecord<>(
                        "order",
                        "userId",
                        id
                )
        );

        sleep(2000);
    }

    public void getUserId(String token){

        kafkaProducer.sendMessage(
                new ProducerRecord<>(
                        "order",
                        "token",
                        token
                )
        );

        sleep(2000);

    }

    private void sleep(Integer time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
