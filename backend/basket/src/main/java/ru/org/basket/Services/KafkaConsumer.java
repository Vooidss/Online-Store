package ru.org.basket.Services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaConsumer {

    private final BasketService basketService;
    private final KafkaProducer kafkaProducer;

    @KafkaListener(topics = "order", groupId = "basket_consumer")
    public void listener(ConsumerRecord<String, String> consumerRecord){

        log.info("Получаем запрос от сервиса Order...");
        log.info("Проверяем есть ли ключ token...");

        System.out.println(consumerRecord.value());

        if(consumerRecord.key().equals("token")){
            String token =  consumerRecord.value();

            log.info("Ключ присутствует: " + consumerRecord.key() + "\n Значение: " + consumerRecord.value());

            log.info("Получаем ID пользователя...");

            Integer userId = 0;

            try {
                userId = basketService.findUserId(token);
            }catch (RuntimeException e){
                log.error("Проблема с получением пользователя.");
                throw new RuntimeException("Проблема с получением пользователя.");
            }

            log.info("ID получен: " + userId);

            kafkaProducer.sendMessage(
                    new ProducerRecord<>(
                            "basket",
                            "userId",
                            userId
                    )
            );



        }
    }

    @KafkaListener(topics = "updateStatus", groupId = "basket_consumer")
    public void updateStatusBasket(ConsumerRecord<String, Map<String,String>> consumerRecord){

        log.info("Получаем данные...");

            log.info("Данные получены: {}", consumerRecord);
            Map<String,String> value = consumerRecord.value();

            if(value.get("userId") != null && !value.get("userId").isEmpty() &&
                    value.get("status") != null && !value.get("status").isEmpty()
            ) {

                log.info("Получаем id...");

                int userId = Integer.parseInt(value.get("userId"));
                log.info("Id получен: {}", userId);

                log.info("Получаем статус...");
                String status = value.get("status");
                log.info("Статус получен: {}", status);

                log.info("Обновляем...");
                basketService.updateStatus(userId, status);
                log.info("Обновили.");
            }
        }
}
