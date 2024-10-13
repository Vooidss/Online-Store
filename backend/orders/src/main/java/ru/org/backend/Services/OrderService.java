package ru.org.backend.Services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.org.backend.DTO.OrderDetails;
import ru.org.backend.DTO.OrderRequest;
import ru.org.backend.DTO.OrderResponse;
import ru.org.backend.Models.Adress;
import ru.org.backend.Models.Order;
import ru.org.backend.Models.Status;
import ru.org.backend.Repositories.OrderRepository;

import java.time.LocalDateTime;

@Service
@Slf4j
@AllArgsConstructor
public class OrderService {

    private final KafkaProducer kafkaProducer;
    private final OrderDetails orderDetails;
    private final OrderRepository orderRepository;
    private final AdressService adressService;

    public ResponseEntity<OrderResponse> arrangeOrder(OrderRequest request, HttpServletRequest httpRequest) {

        log.info("Извлекаем token из заголовка...");
        String token = "";

        try {
            token = httpRequest.getHeader("Authorization").substring(7);
        }catch (RuntimeException e){
            log.error("Ошибка извленчения токена");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(OrderResponse
                            .builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .code(HttpStatus.BAD_REQUEST.value())
                            .message("В заголовке не присутствует Authorization или токен")
                            .build());

        }

        log.info("Токен успешно извлечен");
        log.info("Отправляем запрос на получение ID корзины...");

        kafkaProducer.sendToken(
                new ProducerRecord<>(
                        "order",
                        "token",
                        token
                )
        );

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(orderDetails.getUserId() == null){
            OrderResponse
                    .builder()
                    .status(HttpStatus.NOT_FOUND)
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("Пользователь не найден")
                    .build();
        }

        orderDetails.setOrderRequest(request);

        Adress adress = adressService.generateAdress(
                orderDetails
                        .getOrderRequest()
                        .getAdress(),
                orderDetails
                        .getUserId()
        );
        adress = adressService.save(adress);

        Order order = generateOrder(Status.IN_PROCESSING,adress);

        saveOrder(order);

        return ResponseEntity.ok().body(
                OrderResponse
                        .builder()
                        .order(order)
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .message("Заказ оформляется.")
                        .build()
        );
    }

    public void saveOrder(Order order){
        orderRepository.save(order);
    }

    public Order generateOrder(Status status, Adress adress){
        return Order.builder()
                .userId(orderDetails
                        .getUserId())
                .adressId(adress.getId())
                .nameRecipient(orderDetails
                        .getOrderRequest()
                        .getRecipient()
                        .getName()
                )
                .secondNameRecipient(orderDetails
                        .getOrderRequest()
                        .getRecipient()
                        .getSecondName()
                )
                .phoneRecipient(orderDetails
                        .getOrderRequest()
                        .getRecipient()
                        .getPhone()
                )
                .orderPrice(orderDetails
                        .getOrderRequest()
                        .getOrderPrice()
                )
                .discountPrice(orderDetails
                        .getOrderRequest()
                        .getDiscountPrice()
                )
                .resultPrice(orderDetails
                        .getOrderRequest()
                        .getResultPrice()
                )
                .status(status
                        .getTitle()
                )
                .createdAt(LocalDateTime
                        .now()
                )
                .build();
    }
}
