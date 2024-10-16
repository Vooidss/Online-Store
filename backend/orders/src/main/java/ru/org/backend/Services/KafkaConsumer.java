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
    public void listenerUserId(ConsumerRecord<String, String> consumerRecord){

        if(consumerRecord.key().equals("userId")) {
            log.info(consumerRecord.value());
            orderDetails.setUserId(parseValueToInt(consumerRecord.value()));
            log.info("ID получен");
        }else{
            log.error("Ошибка получения ID");
        }
    }

    @KafkaListener(topics = "authorization", groupId = "order_consumer")
    public void listenerMoneyUser(ConsumerRecord<String, String> consumerRecord){

        if(consumerRecord.key().equals("money")) {

            log.info(consumerRecord.value());

            orderDetails.setUserMoney(parseValueToInt(consumerRecord.value()));
            log.info("Количество денег получено");
        }else{
            orderDetails.setUserMoney(-1);
            log.error("Произошла ошибка при попытки получить сколько денег у пользователя на счету");
        }
    }


    private Integer parseValueToInt(String value){
        return Integer.parseInt(value);
    }

}
