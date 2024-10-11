package ru.org.backend.Services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.org.backend.DTO.OrderDetails;
import ru.org.backend.DTO.ProductResponse;

import java.lang.reflect.Type;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaConsumer{

    private final OrderDetails orderDetails;

    @KafkaListener(topics = "basket", groupId = "order_consumer")
    public void listener(ConsumerRecord<String, String> consumerRecord){

        if(consumerRecord.key().equals("userId")) {
            log.info(consumerRecord.value());
            orderDetails.setUserId(Integer.parseInt(consumerRecord.value()));
            log.info("ID получен");
        }else{
            log.error("Ошибка получения ID");
        }
    }
}
