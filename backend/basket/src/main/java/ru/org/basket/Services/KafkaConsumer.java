package ru.org.basket.Services;

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

    @KafkaListener(topics = "deleteProduct", groupId = "basket_consumer")
    public void deleteAllProductInBasket(ConsumerRecord<String,String> consumerRecord){

        log.info("Получаем данные...");

        if(consumerRecord.key().equals("userId")){
            log.info("Данные получены: {}", consumerRecord);
            log.info("Получаем id...");

            int userId = Integer.parseInt(consumerRecord.value());

            log.info("Id получен: {}",userId);

            log.info("Удаляем...");
            basketService.deleteBasketsById(userId);
            log.info("Удалили.");
        }

    }
}
